package com.emenu.mapper.stock;

import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.dto.stock.DocumentsSearchDto;
import com.emenu.common.entity.stock.StockDocuments;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * StockDocumentsMapper
 *
 * @author: wychen
 * @time: 2017/3/8 20:39
 */
public interface StockDocumentsMapper {

    /**
     * 获取单据list
     *
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listAll() throws SSException;

    /**
     * 通过查询条件获取单据
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    public List<StockDocuments> listDocumentsBySearchDto(@Param("documentsSearchDto")DocumentsSearchDto documentsSearchDto) throws SSException;

    /**
     * 根据Id删除单据
     *
     * @param id
     * @return
     * @throws SSException
     */
    public boolean delById(@Param("id") int id) throws SSException;

    /**
     * 修改单据审核状态
     * @param documentsId
     * @param isAudited
     * @throws Exception
     */
    public void updateIsAudited(@Param("documentsId") int documentsId, @Param("isAudited") int isAudited) throws Exception;

    /**
     * 修改入库单据结算状态
     * @param documentsId
     * @param isSettled
     * @return
     * @throws Exception
     */
    public boolean updateIsSettled(@Param("documentsId") int documentsId, @Param("isSettled") int isSettled) throws Exception;

}
