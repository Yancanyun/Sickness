package com.emenu.service.dish.tag.impl;

import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.printer.DishTagPrinter;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.printer.PrinterDishEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.tag.TagCacheService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.other.ConstantService;
import com.emenu.service.printer.DishTagPrinterService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * TagFacadeServiceImpl
 * 分类对外提供的方法
 * @author dujuan
 * @date 2015/11/2
 */
@Service("tagFacadeService")
public class TagFacadeServiceImpl implements TagFacadeService {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagCacheService tagCacheService;

    @Autowired
    private DishTagPrinterService dishTagPrinterService;

    @Autowired
    private ConstantService constantService;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Tag newTag(Tag tag) throws SSException {
        tag = tagService.newTag(tag);
        return tagCacheService.newTag(tag);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateTag(Tag tag) throws SSException {
        tagService.updateTag(tag);
        tagCacheService.updateTag(tag);

    }

    @Override
    public Tag queryById(int tagId) throws SSException {
        return tagCacheService.queryCloneById(tagId);
    }

    @Override
    public List<Tag> listChildrenByTagId(int tagId) throws SSException {
        return tagCacheService.listChildrenById(tagId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delById(int tagId) throws SSException {
        tagCacheService.delById(tagId);
        tagService.delById(tagId);

    }

    @Override
    public List<TagDto> listByCurrentId(int tagId) throws SSException {
        if(Assert.lessOrEqualZero(tagId)){
            throw SSException.get(EmenuException.TagPIdError);
        }
        return tagCacheService.listDtoByCurrentId(tagId);
    }

    @Override
    public List<Tag> listAllByTagId(int tagId) throws SSException {
        List<Tag> tagList = Collections.emptyList();
        String categoryLayer = constantService.queryValueByKey(ConstantEnum.DishCategoryLayers.getKey());
        if(categoryLayer.equals("2")){
            tagList = tagCacheService.listChildrenById(tagId);
        }else if (categoryLayer.equals("3")){
            tagList = tagCacheService.listGrandsonById(tagId);
        }
        return tagList;
    }

    @Override
    public List<TagDto> listDishByCurrentId(Integer tagId) throws SSException {
        List<Tag> tagList = new ArrayList<Tag>();
        if(Assert.isNull(tagId) || tagId == 0){
            tagList = tagCacheService.listChildrenById(TagEnum.DishAndGoods.getId());
        }else {
            Tag tag = tagCacheService.queryCloneById(tagId);
            tagList.add(tag);
        }
        List<TagDto> tagDtoList = new ArrayList<TagDto>();
        for(Tag tag : tagList){
            List<TagDto> childTagDtoList = tagCacheService.listDtoByCurrentId(tag.getId());
            tagDtoList.addAll(childTagDtoList);
        }
        return tagDtoList;
    }

    @Override
    public List<Tag> listDishTagForPrinter() throws SSException {
        List<Tag> tagList = tagCacheService.listByCurrentId(TagEnum.DishAndGoods.getId());
        List<Tag> newTagList = new ArrayList<Tag>();
        for(Tag tag : tagList){
            Integer tagId = tag.getId();
            if(tagId!=TagEnum.Dishes.getId() && tagId!=TagEnum.Drinks.getId() && tagId!=TagEnum.Others.getId()
                    && tagId!=TagEnum.Goods.getId() && tagId!=TagEnum.Package.getId() && tagId!=TagEnum.DishAndGoods.getId()){
                newTagList.add(tag);
            }
        }
        return newTagList;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public Tag newTagPrinter(Tag tag, Integer printerId) throws SSException {
        //添加分类
        Tag newTag = newTag(tag);
        //添加分类与打印机关联
        DishTagPrinter dishTagPrinter = new DishTagPrinter();
        dishTagPrinter.setDishId(newTag.getId());
        dishTagPrinter.setPrinterId(printerId);
        dishTagPrinter.setType(PrinterDishEnum.TagPrinter.getId());
        dishTagPrinterService.newPrinterDish(dishTagPrinter);
        return newTag;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void updateTagPrinter(Tag tag, Integer printerId) throws SSException {
        //修改该分类信息
        updateTag(tag);
        //修改与打印机关联表
        DishTagPrinter dishTagPrinter = new DishTagPrinter();
        dishTagPrinter.setDishId(tag.getId());
        dishTagPrinter.setPrinterId(printerId);
        dishTagPrinter.setType(PrinterDishEnum.TagPrinter.getId());
        dishTagPrinterService.updatePrinterDish(dishTagPrinter);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void delTagPrinter(int tagId) throws SSException {
        //删除该分类
        delById(tagId);
        //删除与打印机关联表
        dishTagPrinterService.delPrinterDish(tagId, PrinterDishEnum.TagPrinter.getId());
    }

}
