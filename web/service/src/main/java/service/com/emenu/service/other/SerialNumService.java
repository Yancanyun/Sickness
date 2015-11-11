package com.emenu.service.other;

import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.pandawork.core.common.exception.SSException;

/**
 * 流水号Service
 *
 * @author: zhangteng
 * @time: 2015/11/11 17:51
 **/
public interface SerialNumService {

    /**
     * 生成流水号
     *
     * @param templateEnums
     * @return
     * @throws SSException
     */
    public String generateSerialNum(SerialNumTemplateEnums templateEnums) throws SSException;
}
