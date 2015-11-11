package com.emenu.mapper.page;

import com.emenu.common.entity.page.IndexImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * IndexImgMapper
 *
 * @author Wang LiMing
 * @date 2015/10/24 9:33
 */
public interface IndexImgMapper {

    /**
     * 获取所有首页图片
     *
     * @return
     * @throws Exception
     */
    public List<IndexImg> listAll() throws Exception;

    /**
     * 查询当前正在使用图片
     * 0-未使用 1-正在使用
     * 只能查询当前正在使用图片且正在使用图片只能有一个
     *
     * @param state
     * @return
     * @throws Exception
     */
    public IndexImg queryByState(@Param("state") int state) throws Exception;
}
