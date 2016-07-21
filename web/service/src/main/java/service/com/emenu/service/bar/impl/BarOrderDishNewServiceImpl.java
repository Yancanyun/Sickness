package com.emenu.service.bar.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.entity.dish.*;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.bar.BarOrderDishNewService;
import com.emenu.service.dish.*;
import com.emenu.service.dish.tag.TagService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BarOrderDishNewServiceImpl
 * 吧台端新增消费Service
 * @author quanyibo
 * @date: 2016/7/21
 */
@Service("barOrderDishNewService")
public class BarOrderDishNewServiceImpl implements BarOrderDishNewService {

    @Autowired
    TagService tagService;

    @Autowired
    DishTagService dishTagService;

    @Autowired
    DishTasteService dishTasteService;

    @Autowired
    TasteService tasteService;


    @Autowired
    DishService dishService;

    @Override
    public JSONObject queryTag() throws SSException {
        // 一级别分类
        List<Tag> tagList = new ArrayList<Tag>();
        // 二级分类
        List<Tag> bigTagList = new ArrayList<Tag>();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {
            // 查询的时候全部查出来
            tagList = tagService.listAll();
            bigTagList = tagList;
            for(Tag dto1 : tagList) {
                // 若父类Id为1则是一级分类
                if(dto1.getpId()==TagEnum.DishAndGoods.getId()){

                    JSONObject json = new JSONObject();
                    json.put("rootId",dto1.getId());
                    json.put("tagName",dto1.getName());
                    // 查询出一级分来下的所有二级分类
                    JSONArray jsonArrayTemp = new JSONArray();
                    for(Tag dto2 : bigTagList) {
                        if(dto2.getpId()==dto1.getId()){

                            JSONObject temp = new JSONObject();
                            temp.put("tagId",dto2.getId());
                            temp.put("tagName",dto2.getName());
                            jsonArrayTemp.add(temp);
                        }
                    }
                    json.put("tagList",jsonArrayTemp);
                    jsonArray.add(json);
                }
            }
            jsonObject.put("tag",jsonArray);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTagFail,e);
        }
        return jsonObject;
    }

    @Override
    public JSONObject queryAllDishByBigTag(Integer bigTagId) throws SSException {
        List<DishTagDto> dishTagDtos = new ArrayList<DishTagDto>();
        JSONObject jsonObject = new JSONObject();
        try {
            // 根据二级分类查询出所有菜品
            dishTagDtos = dishTagService.listDtoByTagId(bigTagId);
            JSONArray jsonArray = new JSONArray();
            for(DishTagDto dto : dishTagDtos) {

                JSONObject temp = new JSONObject();
                temp.put("dishId",dto.getDishId());
                temp.put("dishName",dto.getDishName());
                temp.put("dishSalePrice",dto.getDishSalePrice());
                temp.put("dishUnit",dto.getDishUnitName());
                temp.put("assistantCode",dto.getDishAssistantCode());

                // 获取菜品具有的全部口味
                List<DishTaste> tastes = new ArrayList<DishTaste>();
                tastes = dishTasteService.listByDishId(dto.getDishId());
                JSONArray jsonArray1 = new JSONArray();
                for(DishTaste dishTaste : tastes) {
                    JSONObject temp1 = new JSONObject();
                    temp1.put("tasteId",dishTaste.getId());
                    // 获得口味名称
                    temp1.put("name",tasteService.queryById(dishTaste.getId()).getName());
                    jsonArray1.add(temp1);
                }
                temp.put("dishTasteList",jsonArray1);

                //获取菜品的关联备注
                List<Remark> remarks = new ArrayList<Remark>();
                remarks = dishService.queryDishRemarkByDishId(dto.getDishId());
                JSONArray jsonArray2 = new JSONArray();
                for(Remark remark : remarks){
                    JSONObject temp1 = new JSONObject();
                    temp1.put("remark",remark.getName());
                    jsonArray2.add(temp1);
                }
                temp.put("dishRemarkList",jsonArray2);
                jsonArray.add(temp);
            }
            jsonObject.put("dishList",jsonArray);
        } catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTagFail,e);
        }
        return jsonObject;
    }

}
