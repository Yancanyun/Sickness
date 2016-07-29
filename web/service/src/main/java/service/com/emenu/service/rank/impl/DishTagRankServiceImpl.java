package com.emenu.service.rank.impl;

import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.entity.dish.DishTag;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.rank.DishSaleRankService;
import com.emenu.service.rank.DishTagRankService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by guofengrui on 2016/7/27.
 */
@Service("dishTagRankService")
public class DishTagRankServiceImpl implements DishTagRankService {
    @Autowired
    private DishSaleRankService dishSaleRankService;

    @Autowired
    private TagService tagService;

    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriod(Date startTime ,Date endTime) throws SSException{
        // 得到相应的菜品销售排行
        List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
        // 菜品大类的销售排行
        List<DishSaleRankDto> dishSaleRankDtoList2 = new ArrayList<DishSaleRankDto>();

        try{
            dishSaleRankDtoList = dishSaleRankService.queryDishSaleRankDtoByTimePeroid(startTime,endTime);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishSaleRankDtoByTimePeriodFailed,e);
        }
        try{
            // 存菜品大类的数量的map
            Map<Integer,BigDecimal> mapQuality = new HashMap<Integer, BigDecimal>();
            // 存菜品大类的总金额的map
            Map<Integer,BigDecimal> mapMoney = new HashMap<Integer, BigDecimal>();
            for(DishSaleRankDto dishSaleRankDto : dishSaleRankDtoList){
                // 获得菜品大类的销售数量
                if(mapQuality.containsKey(dishSaleRankDto.getTagId())){
                    mapQuality.put(dishSaleRankDto.getTagId(),mapQuality.get(dishSaleRankDto.getTagId()).add(BigDecimal.valueOf(dishSaleRankDto.getNum())));
                }else{
                    mapQuality.put(dishSaleRankDto.getTagId(),BigDecimal.valueOf(dishSaleRankDto.getNum()));
                }
                // 获得菜品大类的销售金额
                if(mapMoney.containsKey(dishSaleRankDto.getTagId())){
                    mapMoney.put(dishSaleRankDto.getTagId(),mapMoney.get(dishSaleRankDto.getTagId()).add(dishSaleRankDto.getConsumeSum()));
                }else{
                    mapMoney.put(dishSaleRankDto.getTagId(),dishSaleRankDto.getConsumeSum());
                }
            }
            // 遍历map
            for (Map.Entry<Integer, BigDecimal> entry : mapMoney.entrySet()) {
                DishSaleRankDto dishSaleRankDto = new DishSaleRankDto();
                dishSaleRankDto.setTagId(entry.getKey());
                dishSaleRankDto.setTagName(tagService.queryById(entry.getKey()).getName());
                dishSaleRankDto.setNum(mapQuality.get(entry.getKey()).intValue());
                dishSaleRankDto.setConsumeSum(entry.getValue());
                dishSaleRankDtoList2.add(dishSaleRankDto);
            }
            return dishSaleRankDtoList2;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishTagRankFailed,e);
        }
    }
    @Override
    public List<DishSaleRankDto> listAll() throws SSException{
        // 得到相应的菜品销售排行
        List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
        // 菜品大类的销售排行
        List<DishSaleRankDto> dishSaleRankDtoList2 = new ArrayList<DishSaleRankDto>();

        try{
            dishSaleRankDtoList = dishSaleRankService.listAll();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishSaleRankDtoByTimePeriodFailed,e);
        }
        try{
            // 存菜品大类的数量的map
            Map<Integer,BigDecimal> mapQuality = new HashMap<Integer, BigDecimal>();
            // 存菜品大类的总金额的map
            Map<Integer,BigDecimal> mapMoney = new HashMap<Integer, BigDecimal>();
            for(DishSaleRankDto dishSaleRankDto : dishSaleRankDtoList){
                // 获得菜品大类的销售数量
                if(mapQuality.containsKey(dishSaleRankDto.getTagId())){
                    mapQuality.put(dishSaleRankDto.getTagId(),mapQuality.get(dishSaleRankDto.getTagId()).add(BigDecimal.valueOf(dishSaleRankDto.getNum())));
                }else{
                    mapQuality.put(dishSaleRankDto.getTagId(),BigDecimal.valueOf(dishSaleRankDto.getNum()));
                }
                // 获得菜品大类的销售金额
                if(mapMoney.containsKey(dishSaleRankDto.getTagId())){
                    mapMoney.put(dishSaleRankDto.getTagId(),mapMoney.get(dishSaleRankDto.getTagId()).add(dishSaleRankDto.getConsumeSum()));
                }else{
                    mapMoney.put(dishSaleRankDto.getTagId(),dishSaleRankDto.getConsumeSum());
                }
            }
            // 遍历map
            for (Map.Entry<Integer, BigDecimal> entry : mapMoney.entrySet()) {
                DishSaleRankDto dishSaleRankDto = new DishSaleRankDto();
                dishSaleRankDto.setTagId(entry.getKey());
                dishSaleRankDto.setTagName(tagService.queryById(entry.getKey()).getName());
                dishSaleRankDto.setNum(mapQuality.get(entry.getKey()).intValue());
                dishSaleRankDto.setConsumeSum(entry.getValue());
                dishSaleRankDtoList2.add(dishSaleRankDto);
            }
            return dishSaleRankDtoList2;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishTagAllFailed,e);
        }
    }
    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriodAndPage(Date startTime
                                                                        ,Date endTime
                                                                        ,Integer pageSize
                                                                        ,Integer pageNumber) throws SSException{
        List<DishSaleRankDto> dishSaleRankDtoList = new ArrayList<DishSaleRankDto>();
        try{
            dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeriod(startTime,endTime);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishTagRankFailed,e);
        }
        try{
            if(dishSaleRankDtoList.size()%pageSize==0){
                if(pageNumber == 1){
                    dishSaleRankDtoList = dishSaleRankDtoList.subList(0,pageSize);
                }else{
                    dishSaleRankDtoList = dishSaleRankDtoList.subList(pageSize * (pageNumber - 1), pageSize * pageNumber );
                }
            }else{
                if(pageNumber == 1){
                    dishSaleRankDtoList = dishSaleRankDtoList.subList(0,pageSize);
                }else if(pageNumber == ((dishSaleRankDtoList.size()/pageSize)+1)){
                    dishSaleRankDtoList = dishSaleRankDtoList.subList(pageSize*(pageNumber - 1),dishSaleRankDtoList.size());
                }else{
                    dishSaleRankDtoList = dishSaleRankDtoList.subList(pageSize * (pageNumber - 1), pageSize * pageNumber );
                }
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetPageDishTagRankByTimePeriodAndTagIdFailed,e);
        }
        return dishSaleRankDtoList;
    }
}
