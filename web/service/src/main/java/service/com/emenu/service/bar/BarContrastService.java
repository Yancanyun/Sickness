package com.emenu.service.bar;

import com.emenu.common.entity.bar.BarContrast;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 吧台对账Service
 *
 * @author: yangch
 * @time: 2016/7/27 15:39
 */
public interface BarContrastService {
    /**
     * 新增吧台对账记录
     * @param barContrast
     * @throws SSException
     */
    public void newBarContrast(BarContrast barContrast) throws SSException;

    /**
     * 查询最近一条对账记录
     * @throws SSException
     */
    public BarContrast queryLastBarContrast() throws SSException;

    /**
     * 按倒序查询所有对账记录
     * @return
     * @throws SSException
     */
    public List<BarContrast> listAll() throws SSException;
}
