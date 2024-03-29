package com.emenu.service.page.impl;

import com.emenu.common.entity.page.IndexImg;
import com.emenu.common.enums.other.FileUploadPathEnums;
import com.emenu.common.enums.page.IndexImgEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.page.IndexImgMapper;
import com.emenu.service.other.FileUploadService;
import com.emenu.service.page.IndexImgService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

/**
 * 点餐平台首页图片管理Service实现
 *
 * @author Wang LiMing
 * @date 2015/10/24 9:30
 */

@Service("indexImgService")
public class IndexImgServiceImpl implements IndexImgService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private IndexImgMapper indexImgMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public IndexImg newIndexImg(PandaworkMultipartFile file, HttpServletRequest request) throws SSException {
        if (file.isEmpty()) {
            return null;
        }
        String path = fileUploadService.uploadFile(file, FileUploadPathEnums.IndexImgPath);

        IndexImg indexImg = new IndexImg();
        indexImg.setImgPath(path);
        indexImg.setState(IndexImgEnum.UnUsing.getId());

        return this.newIndexImg(indexImg);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public IndexImg newIndexImg(IndexImg indexImg) throws SSException {

        try {
            if (!checkBeforeSave(indexImg)) {
                return null;
            }

            return commonDao.insert(indexImg);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertIndexImgFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStateById(int id, IndexImgEnum state) throws SSException {

        try {
            if (Assert.lessOrEqualZero(id)) {
                return;
            }
            Assert.isNotNull(state, EmenuException.ImgStateWrong);

            IndexImg indexImg = new IndexImg();
            indexImg.setId(id);
            indexImg.setState(state.getId());
            commonDao.updateFieldsById(indexImg, "state");
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateIndexImgFail, e);
        }
    }

    @Override
    public List<IndexImg> listAll() throws SSException {
        List<IndexImg> list = Collections.emptyList();
        try {
            list = indexImgMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryIndexImgFail, e);
        }
        return list;
    }

    @Override
    public IndexImg queryByState(IndexImgEnum state) throws SSException {

        try {
            if (Assert.isNull(state)){
                return null;
            }

            return indexImgMapper.queryByState(state.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryIndexImgFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {

        try {
            if (Assert.lessOrEqualZero(id)) {
                return;
            }

            commonDao.deleteById(IndexImg.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteIndexImgFail, e);
        }
    }

    /**
     * 检查实体及其关键字段是否为空
     *
     * @param indexImg
     * @return
     * @throws SSException
     */
    private boolean checkBeforeSave(IndexImg indexImg) throws SSException {
        if (Assert.isNull(indexImg)) {
            return false;
        }

        Assert.isNotNull(indexImg.getImgPath(), EmenuException.ImgPathNotNull);
        Assert.isNotNull(indexImg.getState(), EmenuException.ImgStateNotNull);
        Assert.isNotNull(IndexImgEnum.valueOf(indexImg.getState()), EmenuException.ImgStateWrong);

        return true;
    }
}