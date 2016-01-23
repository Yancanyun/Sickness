package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipCard;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * VipCardMapper
 *
 * @author: yangch
 * @time: 2016/1/18 19:19
 */
public interface VipCardMapper {
    /**
     * 查询全部会员卡
     * @return List<VipCard>
     * @throws Exception
     */
    public List<VipCard> listAll() throws Exception;

    /**
     * 分页查询会员卡信息
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VipCard> listByPage(@Param("offset") int offset, @Param("pageSize") int pageSize) throws Exception;

    /**
     * 根据关键词及发卡时间分页查询会员卡信息
     * @param keyword
     * @param startTime
     * @param endTime
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VipCard> listByKeywordAndDate(@Param("keyword") String keyword,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime,
                                              @Param("offset") int offset,
                                              @Param("pageSize") int pageSize) throws Exception;

    /**
     * 查询会员卡总数量
     * @return
     * @throws Exception
     */
    public int countAll() throws Exception;

    /**
     * 查询根据关键词及发卡时间查询出的会员卡数量
     * @return
     * @throws Exception
     */
    public int countByKeywordAndDate(@Param("keyword") String keyword,
                                     @Param("startTime") Date startTime,
                                     @Param("endTime") Date endTime) throws Exception;

    /**
     * 根据ID修改会员卡状态
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id, @Param("status") int status) throws Exception;

    /**
     * 根据PartyID修改会员卡状态
     * @param partyId
     * @param status
     * @throws Exception
     */
    public void updateStatusByPartyId(@Param("partyId") int partyId, @Param("status") int status) throws Exception;

    /**
     * 根据PartyId查询会员卡
     * @return List<VipCard>
     * @throws Exception
     */
    public VipCard queryByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 根据ID修改操作人
     * @param id
     * @param operatorPartyId
     * @throws Exception
     */
    public void updateOperatorById(@Param("id") int id, @Param("operatorPartyId") int operatorPartyId) throws Exception;

    /**
     * 根据ID清空有效期
     * @param id
     * @throws Exception
     */
    public void emptyValidityTimeById(@Param("id") int id) throws Exception;

    /**
     * 查询数据库中最后一条记录
     * @return
     * @throws Exception
     */
    public VipCard queryLastVipCard() throws Exception;
}
