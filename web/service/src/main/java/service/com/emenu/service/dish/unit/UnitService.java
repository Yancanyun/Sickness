package com.emenu.service.dish.unit;

import com.emenu.common.entity.dish.unit.Unit;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * UnitService
 *
 * @author xubaorong
 * @date 2015/10/23
 */
public interface UnitService {

    /**
     * 获取所有单位
     * @throws SSException
     */
    public List<Unit> listAllUnit() throws SSException;

    /**
     * 根据分页获取单位列表
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Unit> listUnitByPage(int curPage, int pageSize) throws SSException;

    /**
     * 获取单位总数
     * @return
     * @throws SSException
     */
    public int countUnit() throws SSException;

    /**
     * 根据id查询一条单位信息
     * @param
     * @param id
     * @throws SSException
     */
    public Unit queryById(int id) throws SSException;

    /**
     * 增加一个单元
     * @param unit
     * @throws SSException
     */
    public Unit newUnit(Unit unit) throws SSException;

    /**
     * 删除一个单元
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 修改一个单元
     * @param unit
     * @throws SSException
     */
    public void updateUnit(Unit unit) throws SSException;
}
