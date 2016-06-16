package com.emenu.mapper.party.group.vip;


import com.emenu.common.entity.party.group.vip.VipInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VipInfoMapper
 *
 * @author chenyuting
 * @time 2015/10/23  10:31
 */
public interface VipInfoMapper {

    /**
     * 根据关键字查看会员信息列表
     * @param keyword
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VipInfo> listByKeyword(@Param("keyword") String keyword,
                                       @Param("offset") int offset,
                                       @Param("pageSize")int pageSize) throws Exception;

    /**
     * 根据关键词计算会员信息数量
     * @param keyword
     * @return
     * @throws Exception
     */
    public int countByKeyword(@Param("keyword") String keyword) throws Exception;

    /**
     * 分页获取会员信息列表
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VipInfo> listByPage(@Param("offset")int offset,
                                    @Param("pageSize")int pageSize) throws Exception;

    /**
     * 查询数据总量
     * @return
     * @throws Exception
     */
    public int countAll() throws Exception;

    /**
     * 根据id查询会员信息
     * @param id
     * @return
     * @throws Exception
     */
    public VipInfo queryById(@Param("id") int id) throws Exception;

    /**
     * 根据partyId查询会员信息
     * @param partyId
     * @return
     * @throws Exception
     */
    public VipInfo queryByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 统计电话号码个数，用于判断是否已经存在电话号码
     * @param phone
     * @return
     * @throws Exception
     */
    public int countByPhone(@Param("phone") String phone) throws Exception;

    /**
     * 根据会员id更改会员状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id,
                                @Param("status") int status) throws Exception;

    /**
     * 根据会员id查询securityUserId，用于改变securityUser状态
     * @param id
     * @return
     * @throws Exception
     */
    public Integer querySecurityUserIdById(@Param("id") int id) throws Exception;

    /**
     * 根据会员类型返回该类型的会员个数，用于判断会员等级是否可以删除
     * @param gradeId
     * @return
     * @throws Exception
     */
    public Integer countByGradeId(@Param("id") int gradeId) throws Exception;

    /**
     * 根据姓名或电话查询会员信息
     * @param keyword
     * @return
     * @throws Exception
     */
    public List<VipInfo> searchByNameOrPhone(@Param("keyword") String keyword) throws Exception;

    /**
     * 根据OpenId、手机号、绑定微信
     * @param openId
     * @param phone
     * @throws Exception
     */
    public void bondWechat(@Param("openId")String openId, @Param("phone")String phone) throws Exception;

    /**
     * 根据OpenId解绑微信
     * @param openId
     * @throws Exception
     */
    public void unbondWechat(@Param("openId")String openId) throws Exception;

    /**
     * 根据OpenId统计有几个匹配
     * @param openId
     * @throws Exception
     */
    public int countByOpenId(@Param("openId")String openId) throws Exception;

    /**
     * 根据手机号查询对应的PartyId
     * @param phone
     * @throws Exception
     */
    public int queryPartyIdByPhone(@Param("phone")String phone) throws Exception;

    /**
     * 根据手机号码统计有多少个不存在OpenId的记录
     * @param phone
     * @return
     * @throws Exception
     */
    public int countNoOpenIdByPhone(@Param("phone") String phone) throws Exception;

    /**
     * 根据OpenId获取会员信息
     * @param openId
     * @return
     * @throws Exception
     */
    public VipInfo queryByOpenId(@Param("openId") String openId) throws Exception;
}
