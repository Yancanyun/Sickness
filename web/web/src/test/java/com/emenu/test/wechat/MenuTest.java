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
        String userInfoUrl = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/wechat/test", false);
        String tableUrl1 = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/mobile/1", true);
        String tableUrl2 = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/mobile/2", true);
        String tableUrl3 = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/mobile/3", true);
        String bondUrl = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/wechat/bond", true);
        String unbondUrl = WechatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/wechat/unbond", true);

        String menuJson = "{\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"view\",\n" +
                "          \"name\":\"获取用户信息测试\",\n" +
                "          \"url\":\"" + userInfoUrl + "\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"会员功能\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"绑定会员\",\n" +
                "               \"url\":\"" + bondUrl + "\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"解绑会员\",\n" +
                "               \"url\":\"" + unbondUrl + "\"\n" +
                "            }]\n" +
                "       },\n" +
                "      {\n" +
                "           \"name\":\"餐饮测试\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"餐台1\",\n" +
                "               \"url\":\"" + tableUrl1 + "\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"餐台2\",\n" +
                "               \"url\":\"" + tableUrl2 + "\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"餐台3\",\n" +
                "               \"url\":\"" + tableUrl3 + "\"\n" +
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