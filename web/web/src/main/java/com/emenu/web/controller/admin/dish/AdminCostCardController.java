package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.mapper.dish.CostCardMapper;
import com.emenu.service.dish.CostCardService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.entity.AbstractEntity;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * AdminCostCardController
 *
 * @author quanyibo && xubaorong
 * @date 2016/5/17.
 */
@Module(value = ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(value = URLConstants.ADMIN_COST_CARD_URL)
public class AdminCostCardController extends AbstractController {

    @Autowired
    CostCardMapper costCardMapper;
    /**
     * 去成本卡页面
     * @param model
     * @return
     */

    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardList)
    @RequestMapping(value = {"","/list"} ,method = RequestMethod.GET)
    public String  toList(Model model) {
        List<Tag> tagList = Collections.EMPTY_LIST;
        try {
            tagList = tagFacadeService.listAllByTagId(TagEnum.Dishes.getId());
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList",tagList);
        return "admin/dish/card/list_cost_card_home";
    }

    /**
     * ajax获取成本卡列表
     * @param searchDto
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardList)
    @RequestMapping(value = "/ajax/list/cost/card/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListCostCard(@PathVariable("pageNo") Integer pageNo
                                      ,@RequestParam("pageSize") Integer pageSize
                                        ,DishSearchDto searchDto)throws SSException
    {
        int offset = (pageNo-1)*pageSize;//查询的偏移量
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<CostCardDto> costCardDto= new ArrayList<CostCardDto>();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonMessage = new JSONObject();
        int dataCount = 0;
        try {
            dataCount=costCardMapper.countBySearchDto(searchDto);
            costCardDto = costCardMapper.queryCostCardDto(offset,searchDto);
            if(Assert.isNotEmpty(costCardDto))
            {
                jsonMessage.put("code",AJAX_SUCCESS_CODE);
                for(CostCardDto dto : costCardDto)
                {
                    JSONObject json = new JSONObject();
                    json.put("id",dto.getId());//成本卡的id
                    json.put("costCardNumber",dto.getCostCardNumber());
                    json.put("name",dto.getName());
                    json.put("assistantNumber",dto.getAssistantNumber());
                    json.put("mainCost",dto.getMainCost());
                    json.put("assistCost",dto.getAssistCost());
                    json.put("deliciousCost",dto.getDeliciousCost());
                    json.put("standardCost",dto.getStandardCost());
                    jsonArray.add(json);
                }
                jsonMessage.put("list",jsonArray);
                jsonMessage.put("dataCount",dataCount);
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return jsonMessage;
    }

    /**
     * 删除成本卡
     * @param
     * @return
     */
    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardDel)
    @RequestMapping(value = "/del" ,method = RequestMethod.GET)
    public void delCostCard(@RequestParam("id")Integer id) throws SSException
    {
        try {
            if(!Assert.lessOrEqualZero(id))
            {
                costCardMapper.delCostCardById(id);
            }
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }

    /**
     * 去添加成本卡页
     * @param
     * @return
     */
    @Module(value = ModuleEnums.AdminDishCostCard, extModule = ModuleEnums.AdminDishCostCardNew)
    @RequestMapping(value = "/add/cost/card",method = RequestMethod.GET)
    public String toAddCostCard()throws SSException
    {
        return "admin/dish/card/add_cost_card_home";
    }
}
