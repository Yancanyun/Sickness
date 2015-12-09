package com.emenu.mapper.table;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.table.Table;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * TableMapper
 *
 * @author: yangch
 * @time: 2015/10/23 10:36
 */
public interface TableMapper {
    /**
     * 查询全部餐台（仅餐台表本身的信息）
     * @return List<Table>
     * @throws Exception
     */
    public List<Table> listAll() throws Exception;

    /**
     * 根据区域ID查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @return List<Table>
     * @throws Exception
     */
    public List<Table> listByAreaId(@Param("areaId") int areaId) throws Exception;

    /**
     * 根据状态查询餐台（仅餐台表本身的信息）
     * @param status
     * @return List<Table>
     * @throws Exception
     */
    public List<Table> listByStatus(@Param("status") int status) throws Exception;

    /**
     * 根据区域及状态查询餐台（仅餐台表本身的信息）
     * @param areaId
     * @param status
     * @return List<Table>
     * @throws Exception
     */
    public List<Table> listByAreaIdAndStatus(@Param("areaId") int areaId, @Param("status") int status) throws Exception;

    /**
     * 根据ID查询餐台状态
     * @param id
     * @return int : 0、停用；1、可用；2、占用已结账；3、占用未结账4、已并桌；5、已预订；6、已删除
     * @throws Exception
     */
    public int queryStatusById(@Param("id") int id) throws Exception;

    /**
     * 查询某餐台名称的数量
     * @param name
     * @return int
     * @throws Exception
     */
    public int countByName(@Param("name") String name) throws Exception;

    /**
     * 根据区域ID查询区域内餐台的数量
     * @param areaId
     * @return int
     * @throws Exception
     */
    public int countByAreaId(@Param("areaId") int areaId) throws Exception;

    /**
     * 根据ID修改餐台状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatus(@Param("id") int id, @Param("status") int status) throws Exception;

    /**
     * 修改餐台二维码
     * @param id
     * @param qrCodePath
     * @throws Exception
     */
    public void updateQrCode(@Param("id") int id, @Param("qrCodePath") String qrCodePath) throws Exception;

    /**
     * 修改餐台开台时间
     * @param id
     * @param openTime
     * @throws Exception
     */
    public void updateOpenTime(@Param("id") int id, @Param("openTime") Date openTime) throws Exception;
}
