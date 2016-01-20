package com.emenu.mapper.vip;

import com.emenu.common.dto.vip.VipCountInfoDto;
import com.emenu.common.entity.vip.VipCountInfo;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * VipCountInfoMapper
 * 会员账号信息Mapper层
 *
 * @author xubr
 * @date 2016/1/18.
 */
public interface VipCountInfoMapper {

    /**
     * 分页获取会员账号信息并按最低消费额即会员等级进行排序
     *
     * @param offset
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<VipCountInfoDto> listByPageAndMin(@Param("offset") int offset,
                                                  @Param("pageSize") int pageSize,
                                                  @Param("orderType")int orderType,
                                                  @Param("orderBy")String orderBy) throws SSException;

    /**
     * 查询所有会员账号数
     *
     * @return
     * @throws Exception
     */
    public int countAll() throws SSException;

    /**
     * 根据会员账号id更改会员账号状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id,
                                 @Param("status") int status) throws Exception;
}
