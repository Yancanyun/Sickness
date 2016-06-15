package com.emenu.service.party.group.vip;


import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;

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
    public int countAll() throws SSException;

    /**
     * 根据关键词获取会员信息列表
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipInfo> listByKeyword(String keyword, int curPage, int pageSize) throws SSException;

    /**
     * 根据关键字查询会员信息数量
     * @param keyword
     * @return
     * @throws SSException
     */
    public int countByKeyword(String keyword) throws SSException;

    /**
     * 增加一条会员信息
     * @param vipInfo
     * @throws SSException
     */
    public VipInfo newVipInfo(Integer userPartyId, VipInfo vipInfo) throws SSException;

    /**
     * 判断手机号是否存在
     * @param phone
     * @return
     * @throws SSException
     */
    public boolean checkPhoneIsExist(Integer id, String phone) throws SSException;

    /**
     * 根据当事人id更新会员信息
     * @param vipInfo
     * @throws SSException
     */
    public void updateVipInfo(VipInfo vipInfo) throws SSException;

    /**
     * 根据id更新会员状态
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateStatusById(int id, UserStatusEnums status) throws SSException;

    /**
     * 根据会员id查询会员信息
     * @param id
     * @return
     * @throws SSException
     */
    public VipInfo queryById(int id) throws SSException;

    /**
     * 根据会员id查询securityUserId，用于改变securityUser状态
     * @param id
     * @return
     * @throws SSException
     */
    public Integer querySecurityUserIdById(int id) throws SSException;

    /**
     * 根据会员类型返回该类型的会员个数，用于判断会员等级是否可以删除
     * @param gradeId
     * @return
     * @throws SSException
     */
    public Integer countByGradeId(int gradeId) throws SSException;

    /**
     * 根据姓名或电话查询会员信息
     * @param keyword
     * @return
     * @throws SSException
     */
    public List<VipInfo> searchByNameOrPhone(String keyword) throws SSException;

    /**
     * 根据OpenId、手机号、密码绑定微信
     * @param openId
     * @param phone
     * @param password
     * @throws SSException
     */
    public void bondWechat(String openId, String phone, String password) throws SSException;

    /**
     * 根据OpenId解绑微信
     * @param openId
     * @throws SSException
     */
    public void unbondWechat(String openId) throws SSException;

    /**
     * 根据OpenId统计有几个匹配
     * @param openId
     * @return
     * @throws SSException
     */
    public int countByOpenId(String openId) throws SSException;

    /**
     * 根据OpenId获取会员信息
     * @param openId
     * @return
     * @throws SSException
     */
    public VipInfo queryByOpenId(String openId) throws SSException;
}
