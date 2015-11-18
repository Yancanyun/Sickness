package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.entity.storage.StorageItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StorageItemMapper
 *
 * @author: zhangteng
 * @time: 2015/11/12 8:49
 **/
public interface StorageItemMapper {

    /**
     * 根据分页和搜索查询
     *
     * @param offset
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<StorageItem> listBySearchDto(@Param("offset") int offset,
                                             @Param("searchDto") StorageItemSearchDto searchDto) throws Exception;

    /**
     * 根据搜索条件查询数量
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public int countBySearchDto(@Param("searchDto") StorageItemSearchDto searchDto) throws Exception;

    /**
     * 查询全部
     *
     * @return
     * @throws Exception
     */
    public List<StorageItem> listAll() throws Exception;

    /**
     * 根据分类ID查询数量
     *
     * @param tagId
     * @return
     * @throws Exception
     */
    public int countByTagId(@Param("tagId") int tagId) throws Exception;

    /**
     * 根据ID修改状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id,
                                 @Param("status") int status) throws Exception;
}
