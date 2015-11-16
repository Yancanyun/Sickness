package com.emenu.service.dish;

import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.enums.dish.DishImgTypeEnums;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;

import java.util.List;

/**
 * 菜品图片Service
 *
 * @author: zhangteng
 * @time: 2015/11/16 10:44
 **/
public interface DishImgService {

    /**
     * 根据菜品id和类型获取图片
     *
     * @param dishId
     * @param typeEnums
     * @return
     * @throws SSException
     */
    public List<DishImg> listByDishIdAndType(int dishId,
                                             DishImgTypeEnums typeEnums) throws SSException;

    /**
     * 添加菜品图片
     *
     * @param dishImg
     * @param image
     * @return
     * @throws SSException
     */
    public DishImg newDishImg(DishImg dishImg,
                              PandaworkMultipartFile image) throws SSException;

    /**
     * 删除菜品图片
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据菜品id和类型删除图片
     *
     * @param dishId
     * @param typeEnums
     * @throws SSException
     */
    public void delByDishIdAndType(int dishId,
                                   DishImgTypeEnums typeEnums) throws SSException;
}
