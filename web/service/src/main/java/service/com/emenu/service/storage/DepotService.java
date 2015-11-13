package com.emenu.service.storage;

import com.emenu.common.entity.storage.Depot;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * DopotService
 * 存放点service层
 * @author xubr
 * @date 2015/11/10.
 */
public interface DepotService {

    /**
     * 分页获取存放点
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<Depot> listByPage(int curPage, int pageSize) throws SSException;

    /**
     * 获取全部存放点
     * @return
     * @throws SSException
     */
    public List<Depot> listAll() throws SSException;

    /**
     * 计算存放点总数
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 根据名称查找存放点
     * @param name
     * @return
     * @throws SSException
     */
    public Depot queryByName(String name) throws SSException;

    /**
     * 添加一个depot
     *
     * @param depot
     * @return
     * @throws SSException
     */
    public Depot newDepot(Depot depot) throws SSException;

    /**
     * 根据id删除存放点
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 修改存放点
     * @param depot
     * @throws SSException
     */
    public void updateDepot(Depot depot) throws SSException;


}
