package com.emenu.service.other.impl;

import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.other.SerialNumMapper;
import com.emenu.service.other.SerialNumService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 流水号Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/11 17:53
 **/
@Service("serialNumService")
public class SerialNumServiceImpl implements SerialNumService {

    @Autowired
    private SerialNumMapper serialNumMapper;

    @Override
    public String generateSerialNum(SerialNumTemplateEnums templateEnums) throws SSException {
        Assert.isNotNull(templateEnums, EmenuException.SerialNumTemplateNotNull);
        String template = templateEnums.getTemplate();
        int firstDelimiterIndex = template.indexOf("|");
        int secondDelimiterIndex = template.indexOf("|", firstDelimiterIndex + 1);
        String prefix = template.substring(0, firstDelimiterIndex);
        String timestampFormat = template.substring(firstDelimiterIndex + 1, secondDelimiterIndex);
        String numberFormat = template.substring(secondDelimiterIndex + 1);
        numberFormat = numberFormat.replaceAll("#", "0");

        // 生成流水号
        SimpleDateFormat sdf = new SimpleDateFormat(timestampFormat);
        String timestamp = sdf.format(new Date());
        String serialKey = prefix + timestamp;
        Integer number = 0;
        try {
            number = serialNumMapper.querySerialNum(serialKey);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        DecimalFormat decimalFormat = new DecimalFormat(numberFormat);
        String numberStr = decimalFormat.format(number);

        return serialKey + numberStr;
    }
}
