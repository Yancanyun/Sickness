package com.emenu.service.register.impl;

import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.register.Register;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.service.register.RegisterService;
import com.pandawork.common.reg.pub.strategy.CustomCurDayCanUseStrategy;
import com.pandawork.common.reg.pub.strategy.Validator;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.CommonUtil;
import com.pandawork.core.framework.dao.CommonDao;
import org.apache.commons.fileupload.util.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * RegisterServiceImpl
 *
 * @author: yangch
 * @time: 2016/8/3 9:39
 */
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {
    // 默认检测时间(1小时)
    private static final long DefaultCheckTime = 3600000;

    // 上次检测的时间
    private static long lastCheck = 0;

    // 是否已经注册过
    private boolean registered = false;

    // 验证器
    private Validator validator = null;

    // 系统指纹
    private String sysId;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SecurityUserService securityUserService;

    @Override
    public String getSysId() throws SSException {
        try {
            String mac = getMac();

            // 把Mac地址进行两次MD5加密，并全部转换为大写字母
            String code = CommonUtil.md5(mac).toUpperCase();
            code = CommonUtil.md5(code).toUpperCase();

            // 转换为大小写间隔的形式(小写大写小写大写...)，并截取前15位
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < code.length(); i++) {
                String sub = code.substring(i, i + 1);

                if ((i % 2) == 0) {
                    sub = sub.toLowerCase();
                }
                stringBuilder.append(sub);
            }
            code = stringBuilder.toString().substring(0, 15);

            // 进行一次替换加密(配对关系来自于旧版)
            Map<String, String> map = new HashMap<String, String>();
            map.put("1", "X");
            map.put("2", "U");
            map.put("3", "T");
            map.put("4", "S");
            map.put("5", "R");
            map.put("6", "Q");
            map.put("7", "P");
            map.put("8", "O");
            map.put("9", "N");
            map.put("0", "M");
            map.put("A", "L");
            map.put("B", "K");
            map.put("C", "J");
            map.put("D", "I");
            map.put("E", "H");
            map.put("F", "G");
            map.put("a", "F");
            map.put("b", "E");
            map.put("c", "D");
            map.put("d", "C");
            map.put("e", "B");
            map.put("f", "A");
            stringBuilder = new StringBuilder();
            for (char c : code.toCharArray()) {
                stringBuilder.append(map.get(String.valueOf(c)));
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public Boolean isRegistered() throws SSException {
        try {
            // 每隔一小时检测一次，一小时内直接返回上次的结果
//            if (System.currentTimeMillis() - lastCheck <= DefaultCheckTime) {
//                return registered;
//            }

            // 从数据库里获取系统指纹及注册码
            Register register = commonDao.queryById(Register.class, 1);

            // 若数据库里不存在注册信息，直接返回未注册
            if (Assert.isNull(register)) {
                registered = false;
                lastCheck = System.currentTimeMillis();
                return registered;
            }

            // 获取系统指纹
            sysId = this.getSysId();

            // 使用验证器对指纹及注册码进行验证
            validator = new CustomCurDayCanUseStrategy(sysId);
            registered = validator.validat(register.getKey());
            lastCheck = System.currentTimeMillis();
            return registered;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public void uploadLicence(InputStream inputStream, String password) throws SSException {
        try {
            // 验证管理员密码
            SecurityUser securityUser = securityUserService.queryById(1);
            password = CommonUtil.md5(password);
            if(!password.equalsIgnoreCase(securityUser.getPassword())) {
                throw SSException.get(EmenuException.PasswordWrong);
            }

            // 从文件中读取注册码
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Streams.copy(inputStream, byteArrayOutputStream, true);

            // 更新系统指纹及注册码
            Register register = commonDao.queryById(Register.class, 1);
            if (Assert.isNull(register)) {
                register = new Register();
                register.setId(1);
                register.setSysId(getSysId());
                register.setKey(byteArrayOutputStream.toString());
                commonDao.insert(register);
            } else {
                register.setSysId(getSysId());
                register.setKey(byteArrayOutputStream.toString());
                commonDao.update(register);
            }

            // 将上次检测时间设为空，让系统立即开始检测是否已注册
            lastCheck = 0;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    /**
     * 获取所有网卡的Mac地址
     * @return
     */
    private String getMac() throws SSException {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Enumeration<NetworkInterface> el = NetworkInterface.getNetworkInterfaces();
            while (el.hasMoreElements()) {
                byte[] macs = el.nextElement().getHardwareAddress();
                if (macs == null)
                    continue;
                for (byte b : macs) {
                    String strHex = Integer.toHexString(b).toString();
                    stringBuilder.append((strHex.length() > 2 ? strHex.substring(strHex.length() - 2) : strHex));
                }
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }
}
