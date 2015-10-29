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
     * @param curPage
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<VipInfo> listVipInfoByKeyword(@Param("keyword") String keyword,
                                              @Param("curPage") int curPage,
                                              @Param("pageSize")int pageSize) throws Exception;

    /**
     * 根据关键词计算会员信息数量
     * @param keyword
     * @return
     * @throws Exception
     */
    public int countVipInfoByKeyword(@Param("keyword") String keyword) throws Exception;

    public List<VipInfo> listVipInfo(@Param("offset")int offset,
                                     @Param("pageSize")int pageSize) throws Exception;

    public int count() throws Exception;

    /**
     * 根据id查询会员信息
     * @param id
     * @return
     * @throws Exception
     */
    public VipInfo queryVipInfoById(@Param("id") int id) throws Exception;

    public int countVipInfoByPhone(@Param("phone") String phone) throws Exception;

    /**
     * 根据会员id更改会员状态
     * @param id
     * @param state
     * @throws Exception
     */
    public void updateStateById(@Param("id") int id,
                                @Param("state") int state) throws Exception;
}
