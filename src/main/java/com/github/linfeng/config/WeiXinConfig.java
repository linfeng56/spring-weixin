package com.github.linfeng.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信配置
 *
 * @author 黄麟峰
 * @date 2020-12-01 17:51
 */
@Component
public class WeiXinConfig {

    @Value("${weixin.appid}")
    private String appid = "";

    @Value("${weixin.appSecret}")
    private String appSecret = "";


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
