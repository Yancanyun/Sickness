package com.emenu.service.table;

import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.TableMerge;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * TableMergeService
 *
 * @author: yangch
 * @time: 2016/6/23 9:14
 */
public interface TableMergeService {
    /**
     * 根据MergeId列出参与此并台的所有餐台的并台信息
     * @param mergeId
     * @return
     * @throws SSException
     */
    public List<TableMerge> listByMergeId(int mergeId) throws SSException;

    /**
     * 根据TableId查询此TableId参与的并台信息
     * @param tableId
     * @return
     * @throws SSException
     */
    public TableMerge queryByTableId(int tableId) throws SSException;

    /**
     * 查询并台表中最后一个MergeId
     * @return
     * @throws SSException
     */
    public int queryLastMergeId() throws SSException;

    /**
     * 根据TableIdList并台
     * @param tableIdList
     * @return
     * @throws SSException
     */
    public List<TableMerge> mergeTable(List<Integer> tableIdList) throws SSException;

    /**
     * 根据TableId取消并台
     * @param tableId
     * @throws SSException
     */
    public void cancelTableMerge(int tableId) throws SSException;

    /**
     * 根据TableId删除t_table_merge表中的内容
     * @param tableId
     * @throws SSException
     */
    public void delTableMergeInfo(int tableId) throws SSException;

    /**
     * 根据TableId查询与该餐台并台的其它餐台
     * @param tableId
     * @return
     * @throws SSException
     */
    public List<Table> listOtherTableByTableId(int tableId) throws SSException;
}
