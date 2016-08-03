package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipAccountInfoDto;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.enums.vip.StatusEnums;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * VipAccountInfoService
 * 会员账户信息Service层
 *
 * @author xubr
 * @date 2016/1/18.
 */
public interface VipAccountInfoService {

    /**
     * 根据最低消费额获取会账户等级列表
     *
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipAccountInfoDto> listByPageAndMin(int curPage,
                                                    int pageSize,
                                                    int orderType,
                                                    String orderBy,
                                                    String keyWord,
                                                    List<Integer> gradeIdList) throws SSException;

    /**
     * 查询所有会员账户数
     *
     * @return
     * @throws Exception
     */
    public int countAll() throws SSException;

    /**
     * 添加会员账户信息
     *
     * @param vipAccountInfo
     * @return
     */
    public VipAccountInfo newVipAccountInfo(VipAccountInfo vipAccountInfo) throws SSException;

    /**
     * 修改会员账户信息
     *
     * @param vipAccountInfo
     * @throws SSException
     */
    public void updateVipAccountInfo(VipAccountInfo vipAccountInfo) throws SSException;

    /**
     * 根据主键id删除会员账户信息
     *
     * @param id
     * @throws SSException
     */
    public void deleteVipAccountInfo(Integer id) throws SSException;

    /**
     * 根据id更新会员账户状态
     *
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateStatusById(int id, StatusEnums status) throws SSException;

    /**
     * 根据openId获取会员账户信息
     * @param openId
     * @return
     * @throws SSException
     */
    public VipAccountInfoDto queryByOpenId(String openId) throws SSException;

    /**
     * 根据partyId查询会员账户信息
     * @param partyId
     * @return
     * @throws SSException
     */
    public VipAccountInfo queryByPartyId(int partyId) throws SSException;

    /**
     * 根据keyWord和gradeIdList查询对应的数据条数
     * @param keyWord
     * @param gradeIdList
     * @return
     * @throws SSException
     */
    public int CountByKeywordAndGrade(String keyWord,List<Integer> gradeIdList) throws SSException;
}
