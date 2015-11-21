package com.emenu.service.dish.tag.impl;

import com.emenu.common.dto.dish.tag.TagChildDto;
import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.dish.TagMapper;
import com.emenu.service.dish.tag.TagCacheService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TagCacheServiceImpl
 * 菜品原料类别缓存管理实现
 * @author dujuan
 * @date 2015/10/22
 */
@Service("tagCacheService")
public class TagCacheServiceImpl implements TagCacheService {

    @Autowired
    @Qualifier("tagMapper")
    private TagMapper tagMapper;

    //类别缓存
    private Map<Integer, TagDto> tagCache = new ConcurrentHashMap<Integer, TagDto>();

    /**
     * 初始化缓存
     * @throws Exception
     */
    @Override
    @PostConstruct
    public void initCache() throws Exception {
        //获取所有的类别
        List<Tag> tagList = tagMapper.listAll();
        //第一次遍历，初始化所有DTO
        for (Tag tag : tagList){
            TagDto tagDto = new TagDto();
            tagDto.setTag(tag);
            tagCache.put(tag.getId(), tagDto);
        }
        // 第二次遍历，设置儿子属性
        for (Tag tag : tagList){
            //如果pid为0则表示无父亲节点
            if (tag.getpId() == 0 || tag.getpId() < 0) {
                continue;
            }
            //否则获取该节点父亲节点的DTO，把该节点放到父亲节点的childrenTagMap中
            TagDto tagDto = tagCache.get(tag.getpId());
            if(tagDto !=null){
                tagDto.addChildMap(tag);
            }else {
                throw SSException.get(EmenuException.TagIdError);
            }
        }
    }

    @Override
    public void refreshCache() throws Exception {
        //刷新缓存即重新调用初始化缓存
        initCache();
    }

    @Override
    public Tag queryParentById(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        TagDto tagDto = tagCache.get(tagId);
        //如果该节点不存在则返回空
        if(tagDto == null) {
            return null;
        }
        //如果父亲节点ID为0则返回空
        Integer tagPid = tagDto.getTag().getpId();
        if(tagPid == 0 || tagPid < 0){
            return null;
        }
        //如果父亲节点为空则返回空
        TagDto parentTagDto = tagCache.get(tagPid);
        if(parentTagDto == null){
            return null;
        }
        //返回父亲节点Tag的克隆对象，防止内存数据被修改
        return (Tag) parentTagDto.getTag().clone();
    }

    @Override
    public List<Tag> listChildrenById(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        //如果该节点不存在则返回空
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto == null) {
            return null;
        }
        //获取儿子节点的Map
        Map<TagChildDto,Integer> tagChildDtoMap = tagDto.getChildrenTagMap();
        //非空判断
        if(tagChildDtoMap == null || tagChildDtoMap.isEmpty()){
            return null;
        }
        List<Tag> childTagList = new ArrayList<Tag>();
        for(Integer childTagId : tagChildDtoMap.values()){
            Tag tag = tagCache.get(childTagId).getTag();
            childTagList.add((Tag) tag.clone());
        }
        return childTagList;
    }

    @Override
    public List<Tag> listGrandsonById(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        //如果该节点不存在则返回空
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto == null) {
            return null;
        }
        List<Tag> tagList = new ArrayList<Tag>();
        //获取儿子节点
        List<Tag> childrenTagList = listChildrenById(tagId);
        for(Tag childrenTag : childrenTagList){
            List<Tag> grandsonTagList = listChildrenById(childrenTag.getId());
            if(grandsonTagList!=null) {
                for (Tag grandsonTag : grandsonTagList) {
                    tagList.add((Tag) grandsonTag.clone());
                }
            }
        }
        return tagList;
    }

    @Override
    public Tag queryRootById(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto == null){
            return null;
        }
        //获取根节点的Tag
        while(tagCache.get(tagId).getTag().getpId() != 0){
            tagId = tagCache.get(tagId).getTag().getpId();
        }
        return (Tag) tagCache.get(tagId).getTag().clone();
    }

    @Override
    public List<Tag> listAllRootByType(int type) throws Exception {
        List<Tag> rootTagList = new ArrayList<Tag>();
        if(type == 0 ) {
            for (TagDto tagDto : tagCache.values()) {
                Tag tag = tagDto.getTag();
                if (tag.getpId() == 0) {
                    rootTagList.add((Tag)tag.clone());
                }
            }
        }else {
            for (TagDto tagDto : tagCache.values()) {
                Tag tag = tagDto.getTag();
                if (tag.getpId() == 0) {
                    rootTagList.add((Tag)tag.clone());
                }
            }
        }
        //按权重进行排序
        Collections.sort(rootTagList);
        return rootTagList;
    }

    @Override
    public List<Tag> listAll() throws Exception {
        //获取所有根节点
        List<Tag> tagRootList = listAllRootByType(0);
        List<Tag> tagList = new ArrayList<Tag>();
        for (Tag tag : tagRootList){
            tagList = dfsTagById(tag.getId(), tagList);
        }
        return tagList;
    }

    @Override
    public List<Tag> listByCurrentId(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        List<Tag> tagList = new ArrayList<Tag>();
        return dfsTagById(tagId, tagList);
    }

    @Override
    public List<TagDto> listDtoByCurrentId(int tagId) throws Exception{
        return dfslistById(tagId);
    }
    /**
     * 递归遍历该节点下的子节点组成嵌套List
     * @param tagId
     * @return
     * @throws Exception
     */
    private List<TagDto> dfslistById(int tagId) throws Exception{
        List<Tag> tagList = listChildrenById(tagId);
        List<TagDto> tagDtoList = new ArrayList<TagDto>();
        if(tagList != null && tagList.size() > 0) {
            for (Tag tag : tagList) {
                TagDto tagDto = new TagDto();
                tagDto.setTag(tag);
                List<TagDto> childTagDtoList = dfslistById(tag.getId());
                tagDto.setChildTagList(childTagDtoList);
                tagDtoList.add(tagDto);
            }
        }
        return tagDtoList;
    }

    @Override
    public Tag queryCloneById(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        TagDto tagDto = tagCache.get(tagId);
        return tagDto == null ? null : (Tag) tagDto.getTag().clone();
    }

    @Override
    public Tag newTag(Tag tag) throws Exception {
        if (!Assert.isNull(tag.getId()) && Assert.lessOrEqualZero(tag.getId())) {
            throw SSException.get(EmenuException.TagIdError);
        }
        //获取父亲节点
        TagDto parentTagDto = tagCache.get(tag.getpId());
        TagDto tagDto = new TagDto();
        tagDto.setTag(tag);
        //把添加的Tag放到缓存中
        tagCache.put(tag.getId(), tagDto);
        //把新的Tag放到父Tag的儿子列表中
        parentTagDto.addChildMap(tag);
        return (Tag) tag.clone();
    }

    @Override
    public void delById(int tagId) throws Exception {
        if (!Assert.isNull(tagId) && Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        //如果该节点下有子节点不能删除
        TagDto tagDto = tagCache.get(tagId);
        Map<TagChildDto, Integer> childrenMap = tagDto.getChildrenTagMap();
        if(childrenMap != null && childrenMap.size() != 0 && !childrenMap.isEmpty()){
            throw SSException.get(EmenuException.TagChildrenIsNull);
        }
        Integer pid = tagDto.getTag().getpId();
        if(pid != 0){
            TagDto fatherTagDto = tagCache.get(pid);
            //删除父亲的key
            fatherTagDto.removeChildMap(tagId, tagDto.getTag().getWeight());
        }
        //删除Tag
        tagCache.remove(tagDto.getTag().getId());
    }

    @Override
    public void updateTag(Tag tag) throws Exception {
        if (!Assert.isNull(tag.getId()) && Assert.lessOrEqualZero(tag.getId())) {
            throw SSException.get(EmenuException.TagIdError);
        }
        TagDto tagDto = tagCache.get(tag.getId());
        if(tagDto != null){
            if(tag.getName()!=null){
                tagDto.getTag().setName(tag.getName());
            }
            if(tag.getWeight()!=null){
                updateWeight(tag.getId(), tag.getWeight());
            }
            if(tag.getpId()!=null){
                updatePid(tag.getId(), tag.getpId());
            }
            if(tag.getPrintAfterConfirmOrder()!=null){
                tagDto.getTag().setPrintAfterConfirmOrder(tag.getPrintAfterConfirmOrder());
            }
            if(tag.getMaxPrintNum()!=null){
                tagDto.getTag().setMaxPrintNum(tag.getMaxPrintNum());
            }
            if(tag.getTimeLimit()!=null){
                tagDto.getTag().setTimeLimit(tag.getTimeLimit());
            }
        }
    }

    @Override
    public void updateName(int tagId, String name) throws SSException{
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto != null){
            tagDto.getTag().setName(name);
        }
    }

    @Override
    public void updateWeight(int tagId, int weight) throws Exception{
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto == null){
            return ;
        }
        Integer pid = tagDto.getTag().getpId();
        if(pid == 0){
            tagDto.getTag().setWeight(weight);
        }else {
            Integer oldWeight = tagDto.getTag().getWeight();
            TagDto pTagDto = tagCache.get(pid);
            if(!oldWeight.equals(weight)){
                //先删除以前的成员，再添加新的成员
                pTagDto.removeChildMap(tagId, oldWeight);
                tagDto.getTag().setWeight(weight);
                pTagDto.addChildMap(tagDto.getTag());
            }
        }
    }

    @Override
    public void updatePid(int tagId, int pId) throws Exception{
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto == null){
            return ;
        }
        TagDto fatherTagDto = tagCache.get(tagDto.getTag().getpId());
        if(fatherTagDto == null){
            return ;
        }
        if(pId != fatherTagDto.getTag().getId()){
            //改变pid
            tagDto.getTag().setpId(pId);
            //父亲Tag删除key
            fatherTagDto.removeChildMap(tagId, tagDto.getTag().getWeight());
            //新父亲Tag添加key
            TagDto newFatherTagDto = tagCache.get(pId);
            newFatherTagDto.addChildMap(tagDto.getTag());
        }
    }

    @Override
    public void delCascadeById(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        List<Tag> tagList = Collections.emptyList();
        tagList = dfsTagById(tagId, tagList);
        TagDto tagDto = tagCache.get(tagId);
        Integer pid = tagDto.getTag().getpId();
        if(pid != 0){
            TagDto fatherTagDto = tagCache.get(pid);
            //删除父亲的key
            fatherTagDto.removeChildMap(tagId, tagDto.getTag().getWeight());
        }
        //删除Tag
        for(Tag tag : tagList){
            tagCache.remove(tag.getId());
        }
    }

    @Override
    public List<Tag> listPathById(int tagId) throws Exception {
        if (Assert.lessOrEqualZero(tagId)) {
            throw SSException.get(EmenuException.TagIdError);
        }
        List<Tag> tagList = new ArrayList<Tag>();
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto == null){
            return tagList;
        }
        tagList.add((Tag) (tagDto.getTag().clone()));
        if(tagCache.get(tagId).getTag().getpId() >= 0){
            while (tagId != 0){
                Tag tag = queryParentById(tagId);
                if(tag != null){
                    tagList.add(tag);
                    tagId = tag.getId();
                }else {
                    tagId = 0;
                }
            }
            Collections.reverse(tagList);
        }
        return tagList;
    }

    /**
     * 递归遍历当前Tag节点——获取的是Clone对象
     * @param tagId
     * @param tagList
     * @return
     */
    private List<Tag> dfsTagById(Integer tagId, List<Tag> tagList) throws CloneNotSupportedException {
        TagDto tagDto = tagCache.get(tagId);
        if(tagDto != null){
            tagList.add((Tag) tagDto.getTag().clone());
            for(Integer id : tagDto.getChildrenTagMap().values()){
                dfsTagById(id, tagList);
            }
        }
        return tagList;
    }

}
