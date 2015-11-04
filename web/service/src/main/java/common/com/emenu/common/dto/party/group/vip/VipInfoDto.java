package com.emenu.common.dto.party.group.vip;

import java.util.Date;

/**
 * 员工基本信息Dto
 * @author chenyuting
 * @date 2015/11/3 9:32
 */
public class VipInfoDto {

    //会员姓名
    private String name;

    //性别(0-未说明,1-男,2-女,3-其他)
    private String sex;

    //出生日期
    private String birthday;

    //电话号码
    private String phone;

    //qq号码
    private String qq;

    //邮箱
    private String email;

    //帐号状态(1-启用,2-停用,3-删除)
    private String state;

    //get、set方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}