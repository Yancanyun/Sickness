package com.emenu.test.remark;

import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.service.remark.RemarkService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * RemarkTest
 *
 * @author: yangch
 * @time: 2015/11/7 14:11
 */
public class RemarkTest extends AbstractTestCase {

    @Autowired
    private RemarkService remarkService;

    @Test
    public void newRemark() throws SSException {
        Remark remark = new Remark();
        remark.setName("顾客跑了");
        remark.setRemarkTagId(6);
        remarkService.newRemark(remark);
    }

    @Test
    public void listAllRemark() throws SSException {
        List<Remark> remarkList = remarkService.listAll();

        for (Remark remark:remarkList){
            System.out.println("Name:" + remark.getName() + " remarkTagId:" + remark.getRemarkTagId()
                    + " weight:" + remark.getWeight() + " isCommon:" + remark.getIsCommon()
                    + " relatedCharge:" + remark.getRelatedCharge());
        }
    }

    @Test
    public void listAllRemarkDto() throws SSException {
        List<RemarkDto> remarkDtoList = remarkService.listAllRemarkDto();

        for (RemarkDto remarkDto:remarkDtoList){
            System.out.println("Name:" + remarkDto.getRemark().getName() + " remarkTagId:" + remarkDto.getRemarkTagId()
                    + " weight:" + remarkDto.getRemark().getWeight() + " isCommon:" + remarkDto.getRemark().getIsCommon()
                    + " relatedCharge:" + remarkDto.getRemark().getRelatedCharge()
                    + " remarkTag:" + remarkDto.getRemarkTagName());
        }
    }


    @Test
    public void listByRemarkTagId() throws SSException {
        List<Remark> remarkList = remarkService.listByRemarkTagId(2);

        for (Remark remark:remarkList){
            System.out.println("Name:" + remark.getName() + " remarkTagId:" + remark.getRemarkTagId()
                    + " weight:" + remark.getWeight() + " isCommon:" + remark.getIsCommon()
                    + " relatedCharge:" + remark.getRelatedCharge());
        }
    }

    @Test
    public void listRemarkDtoByRemarkTagId() throws SSException {
        List<RemarkDto> remarkDtoList = remarkService.listRemarkDtoByRemarkTagId(2);

        for (RemarkDto remarkDto:remarkDtoList){
            System.out.println("Name:" + remarkDto.getRemark().getName() + " remarkTagId:" + remarkDto.getRemarkTagId()
                    + " weight:" + remarkDto.getRemark().getWeight() + " isCommon:" + remarkDto.getRemark().getIsCommon()
                    + " relatedCharge:" + remarkDto.getRemark().getRelatedCharge()
                    + " remarkTag:" + remarkDto.getRemarkTagName());
        }
    }

    @Test
    public void queryRemarkById() throws SSException {
        Remark remark = remarkService.queryById(1);

        System.out.println("Name:" + remark.getName() + " remarkTagId:" + remark.getRemarkTagId()
                + " weight:" + remark.getWeight() + " isCommon:" + remark.getIsCommon()
                + " relatedCharge:" + remark.getRelatedCharge());
    }


    @Test
    public void queryRemarkDtoById() throws SSException {
        RemarkDto remarkDto = remarkService.queryRemarkDtoById(1);

        System.out.println("Name:" + remarkDto.getRemark().getName() + " remarkTagId:" + remarkDto.getRemarkTagId()
                + " weight:" + remarkDto.getRemark().getWeight() + " isCommon:" + remarkDto.getRemark().getIsCommon()
                + " relatedCharge:" + remarkDto.getRemark().getRelatedCharge()
                + " remarkTag:" + remarkDto.getRemarkTagName());
    }

    @Test
    public void updateRemark() throws SSException{
        Remark remark = new Remark();
        remark.setId(3);
        remark.setRemarkTagId(1);
        remark.setName("菜里有虫子");
        remarkService.updateRemark(3, remark);
    }

    @Test
    public void delRemarkById() throws SSException{
        remarkService.delById(3);
    }

    @Test
    public void checkNameIsExist() throws SSException{
        System.out.println(remarkService.checkNameIsExist("菜里有虫子"));
    }

    @Test
    public void countByRemarkTagId() throws SSException{
        System.out.println(remarkService.countByRemarkTagId(6));
    }

    @Test
    public void testQueryDishRemark() throws SSException{
        System.out.println(remarkService.queryDishRemarkByDishId(1));
    }
}
