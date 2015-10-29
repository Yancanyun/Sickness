package com.emenu.mapper.other;

import com.emenu.common.entity.other.Constant;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 常量表管理mapper
 *
 * @author gaoyang
 * @date 2013-08-13
 */
public interface ConstantMapper {

    /**
     * 根据key查询value
     * @param key
     * @return
     */
    public String queryValueByKey(@Param("key") String key);

    /**
     * 根据key更改value
     * @param key
     * @param value
     */
    public void updateValueByKey(@Param("key") String key, @Param("value") String value);
	
	
    /**
     * 取出常量表中所有内容
     * @return
     * @throws Exception
     */
    public List<Constant> queryAllConstants() throws Exception;
}
