package com.emenu.mapper.table;

import com.emenu.common.dto.table.QrCodeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * QrCodeMapper
 *
 * @author: yangch
 * @time: 2015/10/24 14:23
 */
public interface QrCodeMapper {
    /**
     * 根据区域ID查询二维码
     * @param areaId
     * @return
     */
    public List<QrCodeDto> listByAreaId(@Param("areaId") int areaId) throws Exception;

    /**
     * 查询全部二维码
     * @return
     */
    public List<QrCodeDto> listAll() throws Exception;
}
