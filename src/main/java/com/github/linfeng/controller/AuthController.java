package com.github.linfeng.controller;

import com.github.linfeng.config.WeiXinConfig;
import com.github.linfeng.model.User;
import com.github.linfeng.service.UserService;
import com.github.linfeng.utils.HttpClientUtils;
import com.github.linfeng.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证控制器.
 *
 * @author 黄麟峰
 * @date 2020-11-29 22:08
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

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


    private String currentBaseUrl(HttpServletRequest request) {
        String ret = "";

        ret = request.getScheme() + request.getServerName() + request.getServerPort() + request.getContextPath();

        return ret;
    }

    /**
     * 第一步请求：用户同意授权，获取code.
     *
     * @param model 模型属性
     * @return 页面路径
     */
    @RequestMapping("/get-code")
    public String getCode(HttpServletRequest request, Model model) {
        // 文档:
        //https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html

        // 格式:https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect

        CodeRequestView requestView = new CodeRequestView();
        requestView.setAppid(weiXinConfig.getAppid());
        requestView.setRedirectUri(currentBaseUrl(request) + "/receive-code/");

        // snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
        // snsapi_userinfo （弹出授权页面，即使在未关注的情况下，只要用户授权，也能获取其信息 ）
        requestView.setScope("snsapi_base");

        String url = requestView.buildGet();

        // model.addAttribute("go-url", url);

        //return "auth/get-code";
        return "redirect:" + url;
    }

    /**
     * 第二步：通过code换取网页授权access_token.
     *
     * @param request HttpRequest
     * @param model   model
     * @return 页面
     */
    @RequestMapping("/receive-code")
    public String receiveCode(HttpServletRequest request, Model model) {
        // 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
        // code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。

        CodeResponseView responseView = new CodeResponseView("");
        responseView.setCode(request.getParameter("code"));
        responseView.setState(request.getParameter("state"));

        // 获取code后，请求以下链接获取access_token：
        // https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code

        AccessTokenRequestView requestView = new AccessTokenRequestView();
        requestView.setAppid(weiXinConfig.getAppid());
        requestView.setSecret(weiXinConfig.getAppSecret());
        requestView.setCode(responseView.getCode());

        String url = requestView.buildGet();

        model.addAttribute("url", url);

        String result = HttpClientUtils.getRequest(url);

        AccessTokenResponseView accessTokenResponseView = new AccessTokenResponseView(result);
        if (!"".equals(accessTokenResponseView.getErrCode())) {
            LOGGER.warn("请求失败!");
        } else {
            // 更新用户的token信息
            userService.updateUserToken(accessTokenResponseView.getOpenid(), accessTokenResponseView.getAccessToken(),
                accessTokenResponseView.getExpiresIn(), accessTokenResponseView.getRefreshToken(),
                accessTokenResponseView.getScope());
        }
        // return "auth/receive-code";
        return "redirect:/get-user-info";
    }

    /**
     * 第三步：刷新access_token（如果需要）.
     * <br/>
     * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权。
     *
     * @param model model
     * @return 页面
     */
    @RequestMapping("/refresh-token")
    public String refreshToken(Model model) {
        //获取第二步的refresh_token后，请求以下链接获取access_token：
        // https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN

        User user = userService.getUser(1);

        RefreshTokenRequestView requestView = new RefreshTokenRequestView();
        requestView.setAppid(weiXinConfig.getAppid());
        requestView.setRefreshToken(user.getRefreshToken());

        String url = requestView.buildGet();
        model.addAttribute("url", url);

        String result = HttpClientUtils.getRequest(url);

        AccessTokenResponseView accessTokenResponseView = new AccessTokenResponseView(result);
        if (!"".equals(accessTokenResponseView.getErrCode())) {
            LOGGER.warn("请求失败!");
        } else {
            // 更新用户的token信息
            userService.updateUserToken(accessTokenResponseView.getOpenid(), accessTokenResponseView.getAccessToken(),
                accessTokenResponseView.getExpiresIn(), accessTokenResponseView.getRefreshToken(),
                accessTokenResponseView.getScope());
        }

        return "auth/refresh-token";
    }

    /**
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo).
     *
     * @param model model
     * @return 页面
     */
    @RequestMapping("/get-user-info")
    public String getUserInfo(Model model) {

        // 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
        // 请求方法
        // http：GET（请使用https协议）
        // https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN

        User user = userService.getUser(1);

        UserInfoRequestView requestView = new UserInfoRequestView();
        requestView.setOpenid(user.getOpenId());
        requestView.setAccessToken(user.getAccessToken());

        String url = requestView.buildGet();

        String result = HttpClientUtils.getRequest(url);

        UserInfoResponseView responseView = new UserInfoResponseView(result);

        model.addAttribute("user-info", responseView);
        return "auth/user-info";
    }

    /**
     * 检验授权凭证（access_token）是否有效.
     *
     * @param model model
     * @return 页面
     */
    @RequestMapping("/check-access-token")
    public String checkAccessToken(Model model) {

        // 请求方法
        // http：GET（请使用https协议）
        // https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID

        User user = userService.getUser(1);

        CheckAccessTokenRequestView requestView = new CheckAccessTokenRequestView();
        requestView.setOpenid(user.getOpenId());
        requestView.setAccessToken(user.getAccessToken());

        String url = requestView.buildGet();

        String result = HttpClientUtils.getRequest(url);

        CheckAccessTokenResponseView responseView = new CheckAccessTokenResponseView(result);

        model.addAttribute("state", responseView.getErrCode().equals("0") ? "true" : "false");

        return "auth/check-access-token";
    }
}
