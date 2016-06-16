package com.emenu.test.wechat;

import com.emenu.service.sms.SmsService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SmsTest
 *
 * @author: yangch
 * @time: 2016/6/15 16:28
 */
public class SmsTest {
    // 智能匹配模板发送接口的http地址
    private static String URI_SEND_SMS = "https://sms.yunpian.com/v1/sms/send.json";

    // 编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

    // APIKEY
    private static String apikey = "b519e83e1c1468add1c0531fa195ce95";

    public static void main(String[] args) {
        String phone = "18643063480";

        // 生成四位验证码
        int[] validCode = new int[4];
        for (int i = 0; i < 4; i++) {
            validCode[i] = (int)(Math.random() * 10);
        }
        String validCodeStr = "" + validCode[0] + validCode[1] + validCode[2] + validCode[3];

        String text = "【聚客多】您正在进行绑定微信操作，验证码为：" + validCodeStr;

        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", phone);

        post(URI_SEND_SMS, params);
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    private static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }
}
