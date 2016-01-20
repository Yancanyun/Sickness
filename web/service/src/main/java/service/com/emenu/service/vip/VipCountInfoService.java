package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipCountInfoDto;
import com.emenu.common.entity.vip.VipCountInfo;
import com.emenu.common.enums.vip.StatusEnums;
import com.pandawork.core.common.exception.SSException;

import java.math.BigDecimal;
import java.util.List;

/**
 * VipCountInfoService
 * 会员账号信息Service层
 *
 * @author xubr
 * @date 2016/1/18.
 */
public interface VipCountInfoService {

    /**
     * 根据最低消费额获取会员账号等级列表
     *
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipCountInfoDto> listByPageAndMin(int curPage,int pageSize,int orderType,String orderBy) throws SSException;

    /**
     * 查询所有会员账号数
     *
     * @return
     * @throws Exception
     */
    public int countAll() throws SSException;

    /**
     * 添加会员账号信息
     *
     * @param vipCountInfo
     * @return
     */
    public VipCountInfo newVipCountInfo(VipCountInfo vipCountInfo) throws SSException;

    /**
     * 修改会员账号信息
     *
     * @param vipCountInfo
     * @throws SSException
     */
    public void updateVipCountInfo(VipCountInfo vipCountInfo) throws SSException;

    /**
     * 根据主键id删除会员信息
     *
     * @param id
     * @throws SSException
     */
    public void deleteVipCountInfo(Integer id) throws SSException;

    /**
     * 根据id更新会员账号状态
     *
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateStatusById(int id, StatusEnums status) throws SSException;

}
