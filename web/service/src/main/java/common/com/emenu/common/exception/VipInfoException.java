package com.emenu.common.exception;

import com.pandawork.core.common.exception.IBizExceptionMes;

/**
 * 会员基本信息管理异常处理类
 * @author chenyuting
 * @date 2015/10/26  14:14
 */
public enum VipInfoException implements IBizExceptionMes {


    PartyIdNotNull("当事人Id不能为空", 1101),
    VipNameNotNUll("用户姓名不能为空", 1102),
    VipPhoneNotNull("电话号码不能为空", 1103),
    VipPhoneNotRepeat("电话号码不能重复", 1104),
    VipIdNotNull("用户id不能为空",1105)
    ;


    private String msg;

    private int code;

    VipInfoException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String getMes() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }


}
