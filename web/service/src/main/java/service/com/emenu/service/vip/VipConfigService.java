package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipConfigDto;
import com.pandawork.core.common.exception.SSException;

/**
 * 会员基本配置Service
 *
 * @author chenyuting
 * @date 2015/11/16 16:18
 */
public interface VipConfigService {

    /**
     * 更新会员基本配置
     * @param vipConfigDto
     * @throws SSException
     */
    public void updateVipConfig(VipConfigDto vipConfigDto) throws SSException;

    /**
     * 获取会员基本配置
     * @return
     * @throws SSException
     */
    public VipConfigDto queryVipConfig() throws SSException;
}
