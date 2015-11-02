package com.emenu.service.page;

import com.emenu.common.entity.page.Keywords;
import com.emenu.common.enums.page.KeywordsEnum;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 搜索风向标-关键字Service
 *
 * @author Wang LiMing
 * @date 2015/10/22 18:09
 */
public interface KeywordsService {

    /**
     * 新增关键字
     *
     * @param keywords
     * @return
     * @throws SSException
     */
    public Keywords newKeywords(Keywords keywords) throws SSException;

    /**
     * 根据id删除关键字
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据类型获取关键字列表
     * 0为点餐平台关键字，1为服务员系统关键字
     * @param type
     * @return
     * @throws SSException
     */
    public List<Keywords> listByType(KeywordsEnum type) throws SSException;
}
