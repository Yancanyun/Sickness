package com.emenu.service.storage;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.storage.Ingredient;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * IngredientService
 * 原配料管理service
 * @author xiaozl
 * @date: 2016/5/14
 */
public interface IngredientService {

    /**
     * 新增原配料
     * @param ingredient
     * @return
     * @throws SSException
     */
    public Ingredient newIngredient(Ingredient ingredient) throws SSException;

    /**
     * 修改原配料信息
     * @param ingredient
     * @throws SSException
     */
    public void updateIngredient(Ingredient ingredient) throws SSException;

    /**
     * 根据原配料id 查询原配料
     * @param id
     * @return
     * @throws SSException
     */
    public Ingredient queryById(int id) throws SSException;

    /**
     * 根据searchDto查询原配料
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Ingredient> listBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException;

    /**
     * 获取所有原配料
     * @return
     * @throws SSException
     */
    public List<Ingredient> listAll() throws SSException;


}
