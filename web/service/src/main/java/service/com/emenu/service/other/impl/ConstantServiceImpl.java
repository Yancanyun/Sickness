package com.emenu.service.other.impl;

import com.emenu.common.entity.other.Constant;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.other.ConstantMapper;
import com.emenu.service.other.ConstantService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 常量表管理
 * User: gaoyang
 * Date: 13-8-16
 * Time: 下午2:50
 *
 * updated by yangzg
 * Date: 14-5-6
 */
@Service("constantService")
public class ConstantServiceImpl implements ConstantService {

    @Autowired
    ConstantMapper constantMapper;

    private Map<String,String> constants = new HashMap<String,String>();


    @PostConstruct
    public void initConstants() throws Exception {
        List<Constant> constantList = constantMapper.queryAllConstants();
        if(constantList!=null){
            for(Constant constant : constantList)
                constants.put(constant.getKey(),constant.getValue());
        }
    }



    @Override
    public String queryValueByKey(String key) throws SSException {
        try {
            return constants.get(key);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public void updateValueByKey(String key, String value) throws SSException {
        try {
            constantMapper.updateValueByKey(key,value);
            constants.put(key, value);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
}
