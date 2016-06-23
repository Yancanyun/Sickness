package com.emenu.mapper.table;

import com.emenu.common.entity.table.TableMerge;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * TableMergeMapper
 *
 * @author: yangch
 * @time: 2016/6/23 9:28
 */
public interface TableMergeMapper {
    /**
     * 根据MergeId列出参与此并台的所有餐台的并台信息
     * @param mergeId
     * @return
     * @throws Exception
     */
    public List<TableMerge> listByMergeId(@Param("mergeId")int mergeId)throws Exception;

    /**
     * 根据TableId查询此TableId参与的并台信息
     * @param tableId
     * @return
     * @throws Exception
     */
    public TableMerge queryByTableId(@Param("tableId")int tableId) throws Exception;

    /**
     * 查询并台表中一共有多少个并台信息
     * @return
     * @throws Exception
     */
    public int countTableMerge() throws Exception;

    /**
     * 查询并台表中最后一个MergeId
     * @return
     * @throws Exception
     */
    public int queryLastMergeId() throws Exception;

    /**
     * 根据TableId删除某个餐台的并台信息
     * @param tableId
     * @throws Exception
     */
    public void delTableMergeByTableId(@Param("tableId")int tableId) throws Exception;

    /**
     * 根据MergeId删除该MergeId下所有的并台信息
     * @param mergeId
     * @throws Exception
     */
    public void delTableMergeByMergeId(@Param("mergeId")int mergeId) throws Exception;
}
