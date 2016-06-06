package com.emenu.test.wechat;

import com.alibaba.fastjson.JSONObject;
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
        String menuJson = "{\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"view\",\n" +
                "          \"name\":\"获取OpenId测试\",\n" +
                "          \"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbfb878a797b866cf&redirect_uri=http://emenu2.pandawork.net/wechat/test&response_type=code&scope=snsapi_base&state=123#wechat_redirect\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"搜索\",\n" +
                "               \"url\":\"http://www.soso.com/\"\n" +
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
