package com.emenu.service.dish;

import com.emenu.common.entity.dish.Taste;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * TasteService
 * 口味管理
 * @author dujuan
 * @date 2015/11/23
 */
public interface TasteService {

    /**
     * 新增口味
     * @return
     * @param taste
     * @throws SSException
     */
    public Taste newTaste(Taste taste) throws SSException;

    /**
     * 根据ID删除
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据ID修改
     * @param taste
     * @throws SSException
     */
    public void updateById(Taste taste) throws SSException;

    /**
     * 根据ID查询
     * @param id
     * @return
     * @throws SSException
     */
    public Taste queryById(int id) throws SSException;

    /**
     * 获取所有口味
     * @return
     * @throws SSException
     */
    public List<Taste> listAll() throws SSException;

}
