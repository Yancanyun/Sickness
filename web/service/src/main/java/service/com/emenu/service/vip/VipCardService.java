package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipCardDto;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.vip.VipCard;
import com.pandawork.core.common.exception.SSException;

import java.util.Date;
import java.util.List;

/**
 * VipCardService
 *
 * @author: yangch
 * @time: 2016/1/18 19:01
 */
public interface VipCardService {
    /**
     * 查询全部会员卡信息（仅包含会员卡表中的信息）
     * @return List<VipCard>
     * @throws SSException
     */
    public List<VipCard> listAll() throws SSException;

    /**
     * 查询全部会员卡信息（包含会员信息及操作人姓名）
     * @return List<VipCardDto>
     * @throws SSException
     */
    public List<VipCardDto> listAllVipCardDto() throws SSException;

    /**
     * 分页查询会员卡信息（仅包含会员卡表中的信息）
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipCard> listByPage(int pageNo, int pageSize) throws SSException;

    /**
     * 分页查询会员卡信息（包含会员信息及操作人姓名）
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipCardDto> listVipCardDtoByPage(int pageNo, int pageSize) throws SSException;

    /**
     * 根据关键词及发卡时间查询会员卡信息（仅包含会员卡表中的信息）
     * @param keyword
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipCard> listByKeywordAndDate(String keyword, Date startTime, Date endTime,
                                              int pageNo, int pageSize) throws SSException;

    /**
     * 根据关键词及发卡时间查询会员卡信息（包含会员信息及操作人姓名）
     * @param keyword
     * @param startTime
     * @param endTime
     * @param pageNo
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipCardDto> listVipCardDtoByKeywordAndDate(String keyword, Date startTime,
                                                           Date endTime, int pageNo,
                                                           int pageSize) throws SSException;

    /**
     * 查询会员卡总数量
     * @return
     * @throws SSException
     */
    public int countAll() throws SSException;

    /**
     * 查询根据关键词及发卡时间查询出的会员卡数量
     * @return
     * @throws SSException
     */
    public int countByKeywordAndDate(String keyword, Date startTime, Date endTime) throws SSException;

    /**
     * 根据ID查询会员卡
     * @param id
     * @return VipCard
     * @throws SSException
     */
    public VipCard queryById(int id) throws SSException;

    /**
     * 发卡
     * @param vipCard
     * @return VipCard
     * @throws SSException
     */
    public VipCard newVipCard(VipCard vipCard) throws SSException;

    /**
     * 修改会员卡
     * @param id
     * @param vipCard
     * @throws SSException
     */
    public void updateVipCard(int id, VipCard vipCard) throws SSException;

    /**
     * 删除会员卡
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 修改会员卡状态
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateStatus(int id, int status) throws SSException;
}
