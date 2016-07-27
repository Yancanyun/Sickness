package com.emenu.service.bar.impl;

import com.emenu.common.entity.bar.BarContrast;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.bar.BarContrastMapper;
import com.emenu.service.bar.BarContrastService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BarContrastServiceImpl
 *
 * @author: yangch
 * @time: 2016/7/27 15:49
 */
@Service("barContrastService")
public class BarContrastServiceImpl implements BarContrastService {
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private BarContrastMapper barContrastMapper;

    @Override
    public void newBarContrast(BarContrast barContrast) throws SSException {
        try {
            commonDao.insert(barContrast);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewBarContrastFailed, e);
        }
    }

    @Override
    public BarContrast queryLastBarContrast() throws SSException {
        try {
            return barContrastMapper.queryLastBarContrast();
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBarContrastFailed, e);
        }
    }

    @Override
    public List<BarContrast> listAll() throws SSException {
        try {
            return barContrastMapper.listAll();
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryBarContrastFailed, e);
        }
    }
}
