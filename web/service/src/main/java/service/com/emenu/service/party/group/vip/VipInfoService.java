package com.emenu.service.party.group.vip;


import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.enums.party.UserStatusEnums;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.util.Pagination;

import java.util.List;

/**
 * 会员信息管理Service
 *
 * @author chenyuting
 * @time 2015/10/23  14:03
 */
public interface VipInfoService {

    /**
     * 分页获取会员信息
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipInfo> listByPage(int curPage, int pageSize) throws SSException;

    /**
     * 查询数据总量
     * @return
     * @throws SSException
     */
    public int count() throws SSException;

    /**
     * 根据关键词获取会员信息列表
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipInfo> listVipInfoByKeyword(String keyword, int curPage, int pageSize) throws SSException;

    /**
     * 根据关键字查询会员信息数量
     * @param keyword
     * @return
     * @throws SSException
     */
    public int countVipInfoByKeyword(String keyword) throws SSException;

    /**
     * 增加一条会员信息
     * @param vipInfo
     * @throws SSException
     */
    public VipInfo newVipInfo(VipInfo vipInfo) throws SSException;

    /**
     * 判断手机号是否存在
     * @param phone
     * @return
     * @throws SSException
     */
    public boolean checkPhoneIsExist(String phone) throws SSException;

    /**
     * 根据当事人id更新会员信息
     * @param vipInfo
     * @throws SSException
     */
    public void updateVipInfo(VipInfo vipInfo) throws SSException;

    /**
     * 根据id更新会员状态
     * @param id
     * @param state
     * @throws SSException
     */
    public void updateVipInfoStateById(int id, UserStatusEnums state) throws SSException;

    /**
     * 根据会员id查询会员信息
     * @param id
     * @return
     * @throws SSException
     */
    public VipInfo queryVipInfoById(int id) throws SSException;


}
