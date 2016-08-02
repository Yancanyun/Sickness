package com.emenu.mapper.vip;

import com.emenu.common.dto.vip.VipAccountInfoDto;
import com.emenu.common.entity.vip.VipAccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * VipAccountInfoMapper
 * 会员账户信息Mapper层
 *
 * @author xubr
 * @date 2016/1/18.
 */
public interface VipAccountInfoMapper {

    /**
     * 分页获取会员账户信息并按最低消费额即会员等级进行排序
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VipAccountInfoDto> listByPageAndMin(@Param("offset") int offset,
                                                  @Param("pageSize") int pageSize,
                                                  @Param("orderType")int orderType,
                                                  @Param("orderBy")String orderBy,
                                                  @Param("keyWord")String keyWord,
                                                  @Param("gradeIdList") List<Integer> gradeIdList) throws Exception;

    /**
     * 查询所有会员账户数
     *
     * @return
     * @throws Exception
     */
    public int countAll() throws Exception;

    /**
     * 根据会员账户id更改会员账户状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id,
                                 @Param("status") int status) throws Exception;

    /**
     * 根据PartyId获取会员账户信息
     * @param partyId
     * @return
     * @throws Exception
     */
    public VipAccountInfoDto queryByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 根据PartyId获取会员账户信息
     * @param partyId
     * @return
     * @throws Exception
     */
    public VipAccountInfo queryEntityByPartyId(@Param("partyId") int partyId) throws Exception;



}
