package com.emenu.service.table;

import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * QrCodeService
 *
 * @author: yangch
 * @time: 2015/10/24 14:12
 */
public interface QrCodeService {
    /**
     * 根据餐台ID创建二维码
     * @param tableId
     * @param webDomain
     * @param request
     * @return
     * @throws SSException
     */
    public String newQrCodeByTableId(int tableId, String webDomain, HttpServletRequest request) throws SSException;

    /**
     * 生成全部餐台的二维码
     * @param webDomain
     * @param request
     * @return
     * @throws SSException
     */
    public void newAllQrCode(String webDomain, HttpServletRequest request) throws SSException;

    /**
     * 下载属于某区域ID的餐台的二维码
     * @param areaId
     * @param request
     * @param response
     * @throws SSException
     */
    public void downloadQrCodeByAreaId(int areaId, HttpServletRequest request, HttpServletResponse response) throws SSException;

    /**
     * 下载全部二维码
     * @param request
     * @param response
     * @throws SSException
     */
    public void downloadAllQrCode(HttpServletRequest request, HttpServletResponse response) throws SSException;
}
