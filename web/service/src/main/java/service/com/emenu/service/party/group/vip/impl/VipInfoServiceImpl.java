package com.emenu.service.party.group.vip.impl;

import com.emenu.common.dto.party.group.vip.VipInfoDto;
import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.enums.party.*;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.CommonUtil;
import com.emenu.common.utils.WebConstants;
import com.emenu.mapper.party.group.vip.VipInfoMapper;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.service.party.group.vip.VipInfoService;
import com.pandawork.core.common.exception.ExceptionMes;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import jxl.format.Format;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 会员信息管理Service实现
 *
 * @author chenyuting
 * @time 2015/10/23  14:20
 */
@Service("vipInfoService")
public class VipInfoServiceImpl implements VipInfoService{

    @Autowired
    private VipInfoMapper vipInfoMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private PartyService partyService;

    @Autowired
    private SecurityUserService securityUserService;


    @Override
    public List<VipInfo> listByPage(int curPage, int pageSize) throws SSException{
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        List<VipInfo> vipInfoList = Collections.<VipInfo>emptyList();
        try{
            if (Assert.lessZero(offset)) {
                return Collections.emptyList();
            }
            vipInfoList = vipInfoMapper.listByPage(offset, pageSize);
        }catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListVipInfoFail, e);
        }
        return vipInfoList;
    }

    @Override
    public int countAll() throws SSException{
        Integer count = 0;
        try{
            count = vipInfoMapper.countAll();
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public List<VipInfo> listByKeyword(String keyword, int curPage, int pageSize) throws SSException{
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        try{
            if (Assert.lessZero(offset)) {
                return Collections.emptyList();
            }
            Assert.isNotNull(keyword, EmenuException.VipInfoKeywordNotNull);
            return vipInfoMapper.listByKeyword(keyword, curPage, pageSize);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    public int countByKeyword(String keyword) throws SSException{
        Integer count = 0;
        try{
            count = vipInfoMapper.countByKeyword(keyword);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public VipInfo newVipInfo(VipInfo vipInfo) throws SSException{
        try{
            if (!checkBeforeSave(vipInfo)){
                return null;
            }

            //首先判断手机号是否存在
            String phone = vipInfo.getPhone();
            if (this.checkPhoneIsExist(vipInfo.getId(), phone)){
                return null;
            }

            //1.先向t_party表添加一条当事人信息
            Party party = new Party();
            party.setPartyTypeId(PartyTypeEnums.Vip.getId());//会员
            party.setCreatedUserId(1);//此处应为当前登录者的id
            Party newParty = this.partyService.newParty(party);
            int partyId = newParty.getId();//获取刚插入数据的partyId

            //2.再向t_party_security_user表添加一条登录信息，默认登录名为手机号码，密码为123456；
            SecurityUser securityUser = new SecurityUser();
            String password = CommonUtil.md5("123456");
            securityUser.setPartyId(partyId);
            securityUser.setLoginName(phone);
            securityUser.setPassword(password);
            securityUser.setAccountType(AccountTypeEnums.Normal.getId());//正常账户
            securityUser.setStatus(EnableEnums.Enabled.getId());
            this.securityUserService.newSecurityUser(securityUser);

            //3.添加t_party_vip_info会员基本信息表
            vipInfo.setPartyId(partyId);
            vipInfo.setState(UserStatusEnums.Enabled.getId());
            commonDao.insert(vipInfo);

            return vipInfo;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    public boolean checkPhoneIsExist(Integer id, String phone) throws SSException{
        Integer count = 0;
        try{
            //如果id为空，则当前为添加会员
            //id不为空，则当前为修改会员信息
            if (id == null){
                count = vipInfoMapper.countByPhone(phone);
            }
            else {
                String oldPhone = this.queryById(id).getPhone();
                if (id != null && oldPhone.equals(phone)){
                    return count < 0;
                }
            }
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return count > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateVipInfo(VipInfo vipInfo) throws SSException{
        try{
            if (!checkBeforeSave(vipInfo)){
                return ;
            }
            Assert.isNotNull(vipInfo);
            commonDao.update(vipInfo);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStateById(int id, UserStatusEnums state) throws SSException{
        Integer stateType = 0;
        Integer securityUserId = 0;
        try {
            CommonUtil.checkId(id, PartyException.UserIdNotNull);
            Assert.isNotNull(state, PartyException.UserStatusIllegal);

            //获取当前更新的状态
            stateType = state.getId();
            //根据会员id获取用户id
            securityUserId = this.querySecurityUserIdById(id);
            //1.更新securityUser表的状态
            //如果当前更新状态为禁用或删除，则更改用户表状态为禁用
            if (stateType == UserStatusEnums.Disabled.getId() || stateType == UserStatusEnums.Deleted.getId()){
                securityUserService.updateStatusById(securityUserId, EnableEnums.Disabled);
            }
            //如果当前更新状态为启用，则更改用户表状态为启用
            if (stateType == UserStatusEnums.Enabled.getId()){
                securityUserService.updateStatusById(securityUserId, EnableEnums.Enabled);
            }
            //2.更新vipInfo的状态
            vipInfoMapper.updateStateById(id, stateType);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    public VipInfo queryById(int id) throws SSException{
        try {
            CommonUtil.checkId(id, EmenuException.VipIdNotNull);
            VipInfo vipInfo = vipInfoMapper.queryById(id);
            return vipInfo;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    private boolean checkBeforeSave(VipInfo vipInfo)throws SSException{
        if (Assert.isNull(vipInfo)){
            return false;
        }
        Assert.isNotNull(vipInfo.getName(), EmenuException.VipNameNotNUll);
        Assert.isNotNull(vipInfo.getPhone(), EmenuException.VipPhoneNotNull);
        return true;
    }

    public Integer querySecurityUserIdById(int id) throws SSException{
        Integer securityUserId = 0;
        try{
            CommonUtil.checkId(id, PartyException.UserIdNotNull);

            securityUserId = vipInfoMapper.querySecurityUserIdById(id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return securityUserId;
    }

}