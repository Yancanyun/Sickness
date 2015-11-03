package com.emenu.service.table.impl;

import com.emenu.common.dto.table.QrCodeDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.WebConstants;
import com.emenu.mapper.table.AreaMapper;
import com.emenu.mapper.table.QrCodeMapper;
import com.emenu.service.other.ConstantService;
import com.emenu.service.table.AreaService;
import com.emenu.service.table.QrCodeService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * QrCodeServiceImpl
 *
 * @author: yangch
 * @time: 2015/10/26 14:00
 */
@Service("qrCodeService")
public class QrCodeServiceImpl implements QrCodeService {
    @Autowired
    private QrCodeMapper qrCodeMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private TableService tableService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    private AreaMapper areaMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public String newQrCode(int tableId, String webDomain, HttpServletRequest request) throws SSException {
        //获取网站域名
        if (Assert.isNull(webDomain)) {
            webDomain = constantService.queryValueByKey(ConstantEnum.WebDomain.getKey());
        }
        //二维码图片名称
        String qrCodePath = "";

        if (Assert.isNotNull(webDomain)) {
            //二维码的内容(每一个点餐页的地址)
            //地址未定，暂时按照老版本的地址来
            String qrCodeContext = webDomain + "/touch/index/" + tableId;

            //获取二维码存储的文件夹
            String qrCodeSavePath = request.getSession().getServletContext().getRealPath("/resources/");

            //二维码图片的存储地址
            qrCodePath = WebConstants.uploadQrCodePath + tableId + ".jpg";

            //生成二维码内容
            int width = 200;
            int height = 200;
            String format = "jpg";
            Hashtable hints = new Hashtable();
            BitMatrix bitMatrix = null;
            try {
                bitMatrix = new MultiFormatWriter().encode(qrCodeContext, BarcodeFormat.QR_CODE, width, height, hints);
            } catch (WriterException e) {
                LogClerk.errLog.error(e);
                throw SSException.get(EmenuException.SystemException, e);
            }
            File outputFile = new File(qrCodeSavePath + qrCodePath);

            //若目录不存在则创建之
            if(!outputFile.exists()){
                outputFile.mkdirs();
            }

            try {
                MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
            } catch (IOException e) {
                LogClerk.errLog.error(e);
                throw SSException.get(EmenuException.SystemException, e);
            }
        }
        return qrCodePath;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void newAllQrCode(String webDomain, HttpServletRequest request) throws SSException {
        List<Table> tableList = Collections.emptyList();
        try {
            //首先更新常量表中的webDomain
            constantService.updateValueByKey(ConstantEnum.WebDomain.getKey(), webDomain);

            tableList = tableService.listAll();

            //为每张餐台生成二维码
            for (Table t : tableList) {
                String path = newQrCode(t.getId(), webDomain, request);
                Table table = new Table();
                table.setId(t.getId());
                table.setQrCodePath(path);
                //强制修改Table表
                tableService.updateTableForce(table);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return ;
    }

    @Override
    public void downloadByAreaId(int areaId, HttpServletRequest request, HttpServletResponse response) throws SSException {
        List<QrCodeDto> qrCodeDtoList = Collections.emptyList();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File downloadFile = null;
        try {
            String contentType = "application/octet-stream";
            //区域名称
            String areaName = areaService.queryById(areaId).getName();
            //获取zip包的路径
            String resourcesPath = request.getSession().getServletContext().getRealPath("/resources/");
            String zipName = resourcesPath + "/upload/qrCode/" + "zipDownloadTemp";
            //获取二维码路径
            qrCodeDtoList = qrCodeMapper.listByAreaId(areaId);
            //打包为zip文件
            zip(resourcesPath, qrCodeDtoList, zipName);
            //下载
            downloadFile = new File(zipName);
            //若目录不存在则创建之
            if(!downloadFile.exists()){
                downloadFile.mkdirs();
            }
            long fileLength = downloadFile.length();
            response.setContentType(contentType);
            //使用UTF-8以解决中英文混用时的乱码问题
            response.setHeader("Content-disposition", "attachment; filename=" + new String(areaName.getBytes("utf-8"), "ISO8859-1") + ".zip");
            response.setHeader("Content-length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downloadFile));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[2048];
            int byteRead;
            while (-1 != (byteRead = bis.read(buffer, 0, buffer.length))) {
                bos.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DownloadQrCodeFail, e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.DownloadQrCodeFail, e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.DownloadQrCodeFail, e);
                }
            }
            //删除生成的zip文件
            if (downloadFile != null) {
                downloadFile.delete();
            }
        }
        return ;
    }

    @Override
    public void downloadAllQrCode(HttpServletRequest request, HttpServletResponse response) throws SSException {
        List<QrCodeDto> qrCodeDtoList = Collections.emptyList();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File downloadFile = null;
        try {
            String contentType = "application/octet-stream";
            //文件名
            String fileName = "全部二维码";
            //获取zip包的路径
            String resourcesPath = request.getSession().getServletContext().getRealPath("/resources/");
            String zipName = resourcesPath + "/upload/qrCode/" + "zipDownloadTemp";
            //获取二维码路径
            qrCodeDtoList = qrCodeMapper.listAll();
            //打包为zip文件
            zip(resourcesPath, qrCodeDtoList, zipName);
            //下载
            downloadFile = new File(zipName);
            //若目录不存在则创建之
            if(!downloadFile.exists()){
                downloadFile.mkdirs();
            }

            long fileLength = downloadFile.length();
            response.setContentType(contentType);
            //使用UTF-8以解决中英文混用时的乱码问题
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1") + ".zip");
            response.setHeader("Content-length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downloadFile));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[2048];
            int byteRead;
            while (-1 != (byteRead = bis.read(buffer, 0, buffer.length))) {
                bos.write(buffer, 0, byteRead);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DownloadQrCodeFail, e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.DownloadQrCodeFail, e);
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.DownloadQrCodeFail, e);
                }
            }
            //删除生成的zip文件
            if (downloadFile != null) {
                downloadFile.delete();
            }
        }
        return ;
    }

    /**
     * 将指定的文件打成zip包
     * @param ctxPath
     * @param qrCodeDtoList
     * @param zipName
     * @throws SSException
     */
    private void zip(String ctxPath, List<QrCodeDto> qrCodeDtoList, String zipName) throws SSException {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zipName));
            for (QrCodeDto qrCodeDto : qrCodeDtoList) {
                File file = new File(ctxPath + qrCodeDto.getQrCodePath());
                FileInputStream fis = new FileInputStream(file);
                //下载全部二维码时，JPG文件名为区域名_餐桌名
                if (qrCodeDto.getAreaName() != null) {
                    out.putNextEntry(new ZipEntry(qrCodeDto.getAreaName() + "_" + qrCodeDto.getTableName() + ".jpg"));
                }
                //按区域下载二维码时，JPG文件名为区域名_餐桌名
                else {
                    out.putNextEntry(new ZipEntry(qrCodeDto.getTableName() + ".jpg"));
                }
                int byteRead;
                byte[] buffer = new byte[2048];
                while (-1 != (byteRead = fis.read(buffer))) {
                    out.write(buffer, 0, byteRead);
                }
                out.closeEntry();
                fis.close();
            }
            out.close();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DownloadQrCodeFail, e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.DownloadQrCodeFail, e);
                }
            }
        }
    }
}