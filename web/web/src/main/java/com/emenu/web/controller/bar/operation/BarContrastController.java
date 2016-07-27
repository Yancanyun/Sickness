package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.bar.BarContrast;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 吧台对账Controller
 *
 * @author: yangch
 * @time: 2016/7/27 16:03
 */
@Controller
@Module(ModuleEnums.BarContrast)
@RequestMapping(value = URLConstants.BAR_CONTRAST_URL)
public class BarContrastController extends AbstractController {
    /**
     * 获取吧台对账窗口的信息
     * @return
     */
    @Module(ModuleEnums.BarContrast)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject barContrastInfo() {
        try {
            BarContrast barContrast = barContrastService.queryLastBarContrast();

            JSONObject jsonObject = new JSONObject();

            // 原有金额
            BigDecimal originalAmount = new BigDecimal(0);

            // 营业收入金额
            BigDecimal incomeAmount = new BigDecimal(0);

            if (Assert.isNotNull(barContrast)) {
                originalAmount = barContrast.getShouldHaveAmount().subtract(barContrast.getExtractAmount());
                incomeAmount = checkoutPayService.queryCashIncomeFromDate(barContrast.getCreatedTime());
                incomeAmount = incomeAmount.add(consumptionActivityService.queryCashRechargeFromDate(barContrast.getCreatedTime()));
            } else {
                // 如果没有任何一个吧台对账单，则将开始时间设置为Java中最早的时间(1970年)，来查询所有的收入
                incomeAmount = checkoutPayService.queryCashIncomeFromDate(new Date(0));
                incomeAmount = incomeAmount.add(consumptionActivityService.queryCashRechargeFromDate(new Date(0)));
            }

            jsonObject.put("originalAmount", originalAmount);
            jsonObject.put("incomeAmount", incomeAmount);
            jsonObject.put("shouldHaveAmount", originalAmount.add(incomeAmount));

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 吧台对账
     * @param uid
     * @param extractAmount
     * @return
     */
    @Module(ModuleEnums.BarContrast)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject barContrast(@RequestParam("uid") int uid,
                                  @RequestParam("extractAmount") BigDecimal extractAmount) {
        try {
            BarContrast barContrast = new BarContrast();
            BarContrast lastBarContrast = barContrastService.queryLastBarContrast();

            // 根据uid获取PartyID
            SecurityUser securityUser = securityUserService.queryById(uid);
            if (Assert.isNull(securityUser)) {
                throw SSException.get(EmenuException.QueryEmployeeInfoFail);
            }
            int partyId = securityUser.getPartyId();

            BigDecimal originalAmount = new BigDecimal(0);
            BigDecimal incomeAmount = new BigDecimal(0);
            if (Assert.isNotNull(lastBarContrast)) {
                originalAmount = lastBarContrast.getShouldHaveAmount().subtract(lastBarContrast.getExtractAmount());
                incomeAmount = checkoutPayService.queryCashIncomeFromDate(lastBarContrast.getCreatedTime());
                // 会员充值的现金收入
                incomeAmount = incomeAmount.add(consumptionActivityService.queryCashRechargeFromDate(barContrast.getCreatedTime()));
            } else {
                // 如果没有任何一个吧台对账单，则将开始时间设置为Java中最早的时间(1970年)，来查询所有的收入
                incomeAmount = checkoutPayService.queryCashIncomeFromDate(new Date(0));
                incomeAmount = incomeAmount.add(consumptionActivityService.queryCashRechargeFromDate(new Date(0)));
            }
            barContrast.setOriginalAmount(originalAmount);
            barContrast.setIncomeAmount(incomeAmount);
            barContrast.setShouldHaveAmount(originalAmount.add(incomeAmount));
            barContrast.setExtractAmount(extractAmount);
            barContrast.setPartyId(partyId);

            barContrastService.newBarContrast(barContrast);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 获取吧台对账历史
     * @return
     */
    @Module(ModuleEnums.BarContrast)
    @RequestMapping(value = "history", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject barContrastHistory() {
        try {
            List<BarContrast> barContrastList = barContrastService.listAll();

            JSONArray jsonArray = new JSONArray();

            if (Assert.isNull(barContrastList) || barContrastList.size() == 0) {
                return sendJsonArray(jsonArray);
            }

            for (BarContrast barContrast : barContrastList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("time", DateUtils.formatDatetime(barContrast.getCreatedTime()));
                jsonObject.put("extractAmount", barContrast.getExtractAmount());
                String name = employeeService.queryByPartyId(barContrast.getPartyId()).getName();
                jsonObject.put("name", name);

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
