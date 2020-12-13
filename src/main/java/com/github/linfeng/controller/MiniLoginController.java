package com.github.linfeng.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.linfeng.config.WeiXinConfig;
import com.github.linfeng.service.UserService;
import com.github.linfeng.utils.HttpClientUtils;
import com.github.linfeng.view.mini.login.JsCodeToSessionRequestView;
import com.github.linfeng.view.mini.login.JsCodeToSessionResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.TreeMap;

/**
 * 小程序登录控制器.
 *
 * @author 黄麟峰
 */
@Controller
@RequestMapping("/mini/login")
public class MiniLoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniLoginController.class);

    /**
     * 微信配置.
     */
    @Autowired
    private WeiXinConfig weiXinConfig;

    /**
     * 用户基本信息服务.
     */
    @Autowired
    private UserService userService;


    /**
     * 获取当前请求路径
     *
     * @param request 请求
     * @return 当前请求路径
     */
    private String currentBaseUrl(HttpServletRequest request) {
        String ret = "";

        ret = request.getScheme() + request.getServerName() + request.getServerPort() + request.getContextPath();

        return ret;
    }

    /**
     * 调用 auth.code2Session 接口，换取 用户唯一标识 OpenID 和 会话密钥 session_key。
     *
     * @param model 模型属性
     * @return 页面路径
     */
    @RequestMapping("/wx-login")
    @ResponseBody
    public String getCode(HttpServletRequest request, Model model) {
        // 文档:
        //https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html

        // GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code

        JsCodeToSessionRequestView requestView = new JsCodeToSessionRequestView();
        requestView.setAppid(weiXinConfig.getAppid());
        requestView.setSecret(weiXinConfig.getAppSecret());
        requestView.setJsCode(request.getParameter("code"));

        String url = requestView.buildGet();
        String result = HttpClientUtils.getRequest(url);
        LOGGER.info(result);

        JsCodeToSessionResponseView responseView = new JsCodeToSessionResponseView(result);
        Map<String, String> ret = new TreeMap<>();
        if ("0".equals(responseView.getErrCode()) || "".equals(responseView.getErrCode())) {
            ret.put("status", "success");
            ret.put("login", "true");
            // TODO:修改成具体的登录标识
            ret.put("loginKey", userService.getUser(1).getId().toString());
            ret.put("openId", responseView.getOpenId());
            ret.put("sessionKey", responseView.getSessionKey());
            ret.put("unionId", responseView.getUnionId());

        } else {
            ret.put("status", "fail");
            ret.put("login", "false");
        }
        String retData = JSONObject.toJSONString(ret);
        LOGGER.info(retData);
        return retData;
    }
}
