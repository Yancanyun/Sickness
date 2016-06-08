package com.emenu.test.wechat;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.utils.WechatUtils;
import com.pandawork.wechat.exception.WeChatException;
import com.pandawork.wechat.utils.WeChatMenuUtil;

/**
 * MenuTest
 *
 * @author: zhangteng
 * @time: 2014/10/25 15:30
 */
public class MenuTest {
    public static void main(String[] args) {
        String openIdUrl = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/wechat/test", true);
        String tableUrl = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/mobile/1", true);

        String menuJson = "{\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"view\",\n" +
                "          \"name\":\"获取OpenId测试\",\n" +
                "          \"url\":\"" + openIdUrl + "\"\n" +                "      },\n" +
                "      {\n" +
                "           \"name\":\"餐饮测试\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"跳转到点餐页面测试\",\n" +
                "               \"url\":\"" + tableUrl + "\"\n" +                "      },\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"绑定pin\",\n" +
                "               \"key\":\"V1001_GOOD\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }";

        try {
            WeChatMenuUtil.create(menuJson);
            JSONObject jsonObject = WeChatMenuUtil.get();
            System.out.println(jsonObject);
        } catch (WeChatException e) {
            e.printStackTrace();
        }
    }
}
