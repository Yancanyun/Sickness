package com.emenu.service.register;

import com.pandawork.core.common.exception.SSException;

import java.io.InputStream;

/**
 * RegisterService
 *
 * @author: yangch
 * @time: 2016/8/3 9:37
 */
public interface RegisterService {
    /**
     * 计算系统指纹(16位)
     * @return
     * @throws SSException
     */
    public String getSysId() throws SSException;

    /**
     * 判断是否已注册
     * @return
     * @throws SSException
     */
    public Boolean isRegistered() throws SSException;

    /**
     * 上传注册文件
     * @param inputStream
     * @param password
     * @throws SSException
     */
    public void uploadLicence(InputStream inputStream, String password) throws SSException;
}
