package com.emenu.service.dish.impl;

import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.enums.dish.DishImgTypeEnums;
import com.emenu.common.enums.other.FileUploadPathEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.DishImgMapper;
import com.emenu.service.dish.DishImgService;
import com.emenu.service.other.FileUploadService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 菜品图片Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/16 10:59
 **/
@Service("dishImgService")
public class DishImgServiceImpl implements DishImgService {

    @Autowired
    private DishImgMapper dishImgMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<DishImg> listByDishIdAndType(int dishId, DishImgTypeEnums typeEnums) throws SSException {
        List<DishImg> list = Collections.emptyList();
        if (Assert.lessOrEqualZero(dishId)
                || Assert.isNull(typeEnums)) {
            return list;
        }

        try {
            list = dishImgMapper.listByDishIdAndType(dishId, typeEnums.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishImgQueryFailed, e);
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public DishImg newDishImg(DishImg dishImg, PandaworkMultipartFile image) throws SSException {
        try {
            if (!checkBeforeSave(dishImg, image)) {
                return null;
            }
            // 先添加到数据库
            dishImg = commonDao.insert(dishImg);

            // 再上传图片
            // TODO: 2015/11/16 需要添加上传图片的大小
            String path = fileUploadService.uploadFile(image, FileUploadPathEnums.DishImgPath, dishImg.getDishId().toString());
            // 更新图片地址
            dishImg.setImgPath(path);
            commonDao.updateFieldsById(dishImg, "imgPath");
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DishImgInsertFailed, e);
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            commonDao.deleteById(DishImg.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delByDishIdAndType(int dishId, DishImgTypeEnums typeEnums) throws SSException {
        if (Assert.lessOrEqualZero(dishId)
                || Assert.isNull(typeEnums)) {
            return ;
        }
        try {
            dishImgMapper.delByDishIdAndType(dishId, typeEnums.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    private boolean checkBeforeSave(DishImg dishImg,
                                    PandaworkMultipartFile image) throws SSException {
        if (Assert.isNull(dishImg)) {
            return false;
        }

        // 非空检查
        if (Assert.isNull(dishImg.getDishId())
                || Assert.lessOrEqualZero(dishImg.getDishId())) {
            throw SSException.get(EmenuException.DishIdError);
        }
        DishImgTypeEnums typeEnums = DishImgTypeEnums.valueOf(dishImg.getImgType());
        Assert.isNotNull(typeEnums, EmenuException.DishImgTypeIllegal);

        if (Assert.isNull(image)
                || image.getSize() == 0) {
            throw SSException.get(EmenuException.DishImgNotNull);
        }

        return true;
    }
}
