package com.github.linfeng.controller;

import com.github.linfeng.config.WeiXinConfig;
import com.github.linfeng.model.User;
import com.github.linfeng.service.UserService;
import com.github.linfeng.utils.HttpClientUtils;
import com.github.linfeng.utils.JsonUtils;
import com.github.linfeng.view.AccessTokenRequestView;
import com.github.linfeng.view.AccessTokenResponseView;
import com.github.linfeng.view.CodeRequestView;
import com.github.linfeng.view.CodeResponseView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 认证控制器
 *
 * @author 黄麟峰
 * @date 2020-11-29 22:08
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    /**
     * 微信配置
     */
    @Autowired
    private WeiXinConfig weiXinConfig;

    /**
     * 用户基本信息服务
     */
    @Autowired
    private UserService userService;

    /**
     * 第一步请求：用户同意授权，获取code
     *
     * @param model 模型属性
     * @return 页面路径
     */
    @RequestMapping("/get-code")
    public String getCode(Model model) {
        // 文档:
        //https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html

        // 格式:https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect

        CodeRequestView requestView = new CodeRequestView();
        requestView.setAppid(weiXinConfig.getAppid());
        requestView.setRedirectUri("https://xxxx.com/receive-code/");

        // snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）
        // snsapi_userinfo （弹出授权页面，即使在未关注的情况下，只要用户授权，也能获取其信息 ）
        requestView.setScope("snsapi_base");

        String url = requestView.buildGet();

        model.addAttribute("go-url", url);

        return "auth/get-code";
    }

    /**
     * 第二步：通过code换取网页授权access_token
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
            userService.UpdateUserToken(accessTokenResponseView.getOpenid(), accessTokenResponseView.getAccessToken(),
                accessTokenResponseView.getExpiresIn(), accessTokenResponseView.getRefreshToken(),
                accessTokenResponseView.getScope());
        }
        return "auth/receive-code";
    }

    /**
     * 第三步：刷新access_token（如果需要）
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

        StringBuilder url = new StringBuilder(256);
        String refreshUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        //  (必须) 公众号的唯一标识
        String appid = weiXinConfig.getAppid();
        //  (必须) 填写为refresh_token
        String grantType = ""; //user.refreshToken;
        //  (必须) 填写通过access_token获取到的refresh_token参数
        String refreshtoken = "";

        url.append(refreshUrl).append("?")
            .append("appid=").append(appid).append("&")
            .append("grant_type=").append(grantType).append("&")
            .append("refresh_token=").append(refreshtoken);

        model.addAttribute("url", url.toString());

        /*
         * 正确时返回的JSON数据包如下：
         *
         * {
         *   "access_token":"ACCESS_TOKEN",
         *   "expires_in":7200,
         *   "refresh_token":"REFRESH_TOKEN",
         *   "openid":"OPENID",
         *   "scope":"SCOPE"
         * }
         */
        String result = HttpClientUtils.getRequest(url.toString());

        // 将返回结果转成JSON/Map格式
        Map<String, String> resultJson = JsonUtils.toMap(result);
        ;

        // 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        String receiveAccessToken = resultJson.get("receive_access_token");
        // access_token接口调用凭证超时时间，单位（秒）
        String receiveExpiresIn = resultJson.get("receive_expires_in");
        // 用户刷新access_token
        String receiveRefreshToken = resultJson.get("receive_refresh_token");
        // 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
        String receiveOpenid = resultJson.get("receive_openid");
        // 用户授权的作用域，使用逗号（,）分隔
        String receiveScope = resultJson.get("receive_scope");

        // 更新用户的token信息
        // userService.UpdateUserToken(receiveOpenid,receiveAccessToken,receiveExpiresIn,receiveRefreshToken,receiveScope);

        return "auth/refresh-token";
    }

    /**
     * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
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

        StringBuilder url = new StringBuilder(256);
        String userInfoUrl = "https://api.weixin.qq.com/sns/userinfo";

        // 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        String accessToken = ""; // user.getAccessToken;
        // 用户的唯一标识
        String openid = ""; // user.getOpenid;
        // 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
        String lang = "zh_CN";

        url.append(userInfoUrl).append("?")
            .append("access_token=").append(accessToken).append("&")
            .append("openid=").append(openid).append("&")
            .append("lang=").append(lang);

        /*
         * 正确时返回的JSON数据包如下：
         *
         * {
         *   "openid":" OPENID",
         *   "nickname": NICKNAME,
         *   "sex":"1",
         *   "province":"PROVINCE",
         *   "city":"CITY",
         *   "country":"COUNTRY",
         *   "headimgurl":"https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
         *   "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],
         *   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
         * }
         *
         */
        String result = HttpClientUtils.getRequest(url.toString());

        // 将返回结果转成JSON/Map格式
        Map<String, String> resultJson = JsonUtils.toMap(result);
        ;

        // 用户的唯一标识
        String receiveOpenid = resultJson.get("openid");
        // 用户昵称
        String receiveNickName = resultJson.get("nickname");
        // 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
        String receiveSex = resultJson.get("sex");
        // 用户个人资料填写的省份
        String receiveProvince = resultJson.get("province");
        // 普通用户个人资料填写的城市
        String receiveCity = resultJson.get("city");
        // 国家，如中国为CN
        String receiveCountry = resultJson.get("country");
        // 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
        String receiveHeadimgurl = resultJson.get("headimgurl");
        // 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
        String receivePrivilege = resultJson.get("privilege");
        // 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
        String receiveUnionid = resultJson.get("unionid");

        model.addAttribute("user-info", resultJson);
        return "auth/user-info";
    }

    /**
     * 检验授权凭证（access_token）是否有效
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

        StringBuilder url = new StringBuilder(256);
        String checkAccessTokenUrl = "https://api.weixin.qq.com/sns/auth";

        // 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
        String accessToken = ""; // user.getAccessToken;
        // 用户的唯一标识
        String openid = ""; // user.getOpenid;

        url.append(checkAccessTokenUrl).append("?")
            .append("access_token=").append(accessToken).append("&")
            .append("openid=").append(openid);

        /*
         * 返回说明 正确的JSON返回结果：
         *
         * { "errcode":0,"errmsg":"ok"}
         */

        String result = HttpClientUtils.getRequest(url.toString());

        // 将返回结果转成JSON/Map格式
        Map<String, String> resultJson = JsonUtils.toMap(result);
        String errorCode = resultJson.get("errcode");
        String errorMsg = resultJson.get("errmsg");

        model.addAttribute("state", errorCode.equals("0") ? "true" : "false");

        return "auth/check-access-token";
    }
}
