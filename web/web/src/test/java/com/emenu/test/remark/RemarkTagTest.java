package com.emenu.test.remark;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.dto.remark.RemarkTagDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.service.remark.RemarkService;
import com.emenu.service.remark.RemarkTagService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * RemarkTagTest
 *
 * @author: yangch
 * @time: 2015/11/7 14:11
 */
public class RemarkTagTest extends AbstractTestCase {

    @Autowired
    private RemarkTagService remarkTagService;

    @Autowired
    private RemarkService remarkService;

    @Test
    public void newRemarkTag() throws SSException {
        RemarkTag remarkTag = new RemarkTag();
        remarkTag.setName("结账失败备注");
        remarkTag.setpId(5);
        remarkTagService.newRemarkTag(remarkTag);
    }

    @Test
    public void listAllRemarkTag() throws SSException {
        List<RemarkTag> remarkTagList = remarkTagService.listAll();

        for (RemarkTag remarkTag:remarkTagList){
            System.out.println("Name:" + remarkTag.getName() + " pid:" + remarkTag.getpId());
        }
    }

    @Test
    public void queryRemarkTagById() throws SSException {
        RemarkTag remarkTag = remarkTagService.queryById(6);

        System.out.println("Name:" + remarkTag.getName() + " pid:" + remarkTag.getpId());
    }

    @Test
    public void updateRemarkTag() throws SSException{
        RemarkTag remarkTag = new RemarkTag();
        remarkTag.setId(6);
        remarkTag.setName("顾客取消结账");
        remarkTagService.updateRemarkTag(6, remarkTag);
    }

    @Test
    public void delRemarkTagById() throws SSException{
        remarkTagService.delById(6);
    }

    @Test
    public void checkNameIsExist() throws SSException{
        System.out.println(remarkTagService.checkNameIsExist("普通备注"));
    }

    @Test
    public void listByParentId() throws SSException {
        int pId = 1;
        List<RemarkTag> remarkTagList = remarkTagService.listByParentId(pId);

        for (RemarkTag remarkTag:remarkTagList){
            System.out.println("Name:" + remarkTag.getName() + " pid:" + remarkTag.getpId());
        }
    }

    @Test
    public void controllerListTest() throws SSException {
        //将第一个上级分类下的所有子分类存为一个List
        List<RemarkTag> firstChildTagList = remarkTagService.listByParentId(1);

        List<RemarkTagDto> firstChildTagDtoList = new ArrayList<RemarkTagDto>();
        for (int i = 0; i < firstChildTagList.size(); i++) {
            RemarkTagDto remarkTagDto = new RemarkTagDto();
            //设置该子分类
            remarkTagDto.setRemarkTag(firstChildTagList.get(i));
            //获取并设置该子分类下的所有备注
            List<RemarkDto> remarkDtoList = remarkService.listRemarkDtoByRemarkTagId(firstChildTagList.get(i).getId());
            remarkTagDto.setRemarkDtoList(remarkDtoList);

            firstChildTagDtoList.add(remarkTagDto);
        }
        firstChildTagDtoList = firstChildTagDtoList;
    }


    @Test
    public void controllerAjaxListTest() throws SSException {
        int pId = 1;

        //将该PID下的所有子分类存为一个List
        List<RemarkTag> childTagList = remarkTagService.listByParentId(pId);

        JSONArray jsonArray = new JSONArray();

        //将该PID下的所有子分类及其所拥有的所有备注放入JSONArray中
        for (RemarkTag remarkTag : childTagList) {
            //获取该子分类下的所有备注
            List<RemarkDto> remarkDtoList = remarkService.listRemarkDtoByRemarkTagId(remarkTag.getId());

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bigTagId", remarkTag.getpId());
            jsonObject.put("bigTagName", remarkTagService.queryById(remarkTag.getpId()).getName());
            jsonObject.put("smallTagId", remarkTag.getId());
            jsonObject.put("smallTagName", remarkTag.getName());

            JSONArray contentList = new JSONArray();
            for (RemarkDto remarkDto : remarkDtoList) {
                JSONObject childJsonObject = new JSONObject();
                childJsonObject.put("contentId", remarkDto.getRemark().getId());
                childJsonObject.put("weight", remarkDto.getRemark().getWeight());
                childJsonObject.put("content", remarkDto.getRemark().getName());
                childJsonObject.put("relatedCharges", remarkDto.getRemark().getRelatedCharges());
                if (remarkDto.getRemark().getIsCommon() == 0) {
                    childJsonObject.put("isCommon", "否");
                }
                if (remarkDto.getRemark().getIsCommon() == 1) {
                    childJsonObject.put("isCommon", "是");
                }
                contentList.add(childJsonObject);
            }

            jsonObject.put("contentList", contentList);
            jsonArray.add(jsonObject);
        }

        jsonArray = jsonArray;
    }
}
