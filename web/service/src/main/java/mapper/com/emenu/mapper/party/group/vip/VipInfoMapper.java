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
    public Integer querySecurityUserIdById(@Param("id")int id) throws Exception;
}
