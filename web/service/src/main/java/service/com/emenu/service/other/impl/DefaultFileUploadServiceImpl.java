package com.emenu.service.other.impl;

import com.emenu.common.enums.other.FileUploadPathEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.ImageUtils;
import com.emenu.service.other.FileUploadService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.sytem.SystemInstance;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.CommonUtil;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * 默认的文件上传实现
 *
 * @author: zhangteng
 * @time: 2015/10/29 15:09
 **/
@Service("defaultFileUploadService")
public class DefaultFileUploadServiceImpl implements FileUploadService {

    private String defaultUploadPath;

    @PostConstruct
    public void init() {
        SystemInstance systemInstance = SystemInstance.getIntance();
        defaultUploadPath = (String) systemInstance.getProperty("defaultUploadPath");
    }

    @Override
    public String uploadFile(PandaworkMultipartFile file, FileUploadPathEnums pathEnums, int width, int height) throws SSException {
        Assert.isNotNull(file, EmenuException.UploadFileNotNull);
        Assert.isNotNull(pathEnums, EmenuException.UploadPathNotNull);
        if (file.isEmpty()) {
            return "";
        }

        // 检查目标文件夹是否存在，不存在则创建
        String tmpPath = pathEnums.getPath();
        if (!tmpPath.startsWith("/")) {
            tmpPath = "/" + tmpPath;
        }
        if (!tmpPath.endsWith("/")) {
            tmpPath += "/";
        }
        String returnPath = defaultUploadPath + tmpPath;
        File path = new File(returnPath);
        boolean mkdirSuccess = true;
        if (!path.isDirectory()) {
            // 创建目录
            synchronized (DefaultFileUploadServiceImpl.class) {
                if (!path.isDirectory()) {
                    mkdirSuccess = path.mkdirs();
                }
            }
        }
        // 创建失败，返回空
        if (!mkdirSuccess) {
            throw SSException.get(EmenuException.UploadDirCreateFail);
        }

        // 对文件名进行md5加密
        File srcFile = file.getFile();
        String fileName = CommonUtil.md5(srcFile) + System.currentTimeMillis();
        fileName = returnPath + fileName;
        String format = CommonUtil.getExtention(file.getOriginalFilename());
        if (!Assert.isNull(format)) {
            fileName += ("." + format);
        }

        // 拷贝文件
        File targetFile = new File(fileName);
        if (CommonUtil.copyFile(srcFile, targetFile)) {
            // 判断是否需要压缩
            if (!Assert.lessOrEqualZero(width)
                    || !Assert.lessOrEqualZero(height)) {
                try {
                    ImageUtils.compressImage(targetFile, width, height);
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ImageCompressedFail, e);
                }
            }
        } else {
            throw SSException.get(EmenuException.ImageCompressedFail);
        }

        return fileName;
    }

    @Override
    public String uploadFile(PandaworkMultipartFile file, FileUploadPathEnums pathEnums) throws SSException {
        return this.uploadFile(file, pathEnums, 0, 0);
    }
}
