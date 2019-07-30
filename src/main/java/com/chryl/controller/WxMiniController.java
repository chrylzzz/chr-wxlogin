package com.chryl.controller;

import com.chryl.bean.WxSessionModel;
import com.chryl.util.HttpClientUtil;
import com.chryl.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 小程序登录
 * Created by Chryl on 2019/7/26.
 */
@RestController
public class WxMiniController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/wxMiniLogin")//小程序login
    public Object wxLogin(String code) {

        //
        Map<String, String> params = new HashMap<>();
        //这两个参数为 小程序的固定值,(在设置->开发设置)
        params.put("appid", "wx0ea14dbf6a424292");
        //注意,秘钥不要传递到前端去
        params.put("secret", "xxxx");
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        //注意wx返回的也不能传递到前端
        String wxResult = null;
        try {
            wxResult = HttpClientUtil.doGet(url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(wxResult);


        //
        WxSessionModel model = JsonUtil.jsonToPojo(wxResult, WxSessionModel.class);

        //存入 session到redis:未配置
//        stringRedisTemplate.opsForValue()
//                .set("user-redis-session:" + model.getOpenid(),
//                        model.getSession_key(),
//                        1000 * 60 * 30
//                );

        System.out.println(code);
        return model;
    }
}