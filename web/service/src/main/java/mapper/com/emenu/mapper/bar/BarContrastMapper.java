package com.emenu.mapper.bar;

import com.emenu.common.entity.bar.BarContrast;

import java.util.List;

/**
 * BarContrastMapper
 *
 * @author: yangch
 * @time: 2016/7/27 15:56
 */
public interface BarContrastMapper {
    /**
     * 查询最近一条对账记录
     * @return
     * @throws Exception
     */
    public BarContrast queryLastBarContrast() throws Exception;

    /**
     * 按倒序查询所有对账记录
     * @return
     * @throws Exception
     */
    public List<BarContrast> listAll() throws Exception;
}
