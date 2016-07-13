package com.emenu.mapper.storage;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.entity.storage.StorageItem;
import com.pandawork.core.common.exception.SSException;
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
     * @param searchDto
     * @return
     * @throws Exception
     */
    public List<StorageItem> listBySearchDto(@Param("searchDto") ItemAndIngredientSearchDto searchDto) throws Exception;

    /**
     * 根据搜索条件查询数量
     *
     * @param searchDto
     * @return
     * @throws Exception
     */
    public int countBySearchDto(@Param("searchDto") ItemAndIngredientSearchDto searchDto) throws Exception;

    /**
     * 根据id进行查询
     *
     * @param id
     * @return
     * @throws SSException
     */
    public StorageItem queryById(@Param("id") int id) throws SSException;

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

    /**
     * 根据关键字进行查询
     * 此方法只用于根据助记码或名字查询
     * 返回的实体中只有id、name、assistantCode三个字段有值
     * 其余查询请使用{@link #listBySearchDto(int, StorageItemSearchDto)}
     *
     * @param keyword
     * @return
     * @throws Exception
     */
    public List<StorageItem> listByKeyword(@Param("keyword") String keyword) throws Exception;

    /**
     * 根据原配料id获取库存物品list
     * @param ingredientId
     * @return
     * @throws SSException
     */
    public List<Integer> listIdsByIngredientId(@Param("ingredientId") Integer ingredientId) throws Exception;

    /**
     * 根据关键字获取库存物品
     * @param keyword
     * @return
     * @throws SSException
     */
    public StorageItem queryByKeyword(String keyword) throws Exception;
}
