package com.emenu.mapper.page;

import com.emenu.common.entity.page.Keywords;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * KeywordsMapper
 *
 * @author Wang LiMing
 * @date 2015/10/23 9:00
 */
public interface KeywordsMapper {

    /**
     * 根据类型获取关键字列表
     * 0为点餐平台关键字，1为服务员系统关键字
     * @param type
     * @return
     */
    public List<Keywords> listByType(@Param("type") int type) throws Exception;

    /**
     * 判断同类型关键字在数据库中是否存在
     * @param key
     * @param type
     * @return
     */
    public Integer countByKeyAndType(@Param("key") String key, @Param("type") int type) throws Exception;
}
