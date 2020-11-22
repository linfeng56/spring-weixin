package com.github.linfeng.plan.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.github.linfeng.plan.holder.LoginUserHolder;
import com.github.linfeng.plan.view.LoginUser;
import com.github.linfeng.plan.view.ResponseView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 检查用户登录拦截器
 *
 * @author 黄麟峰
 * @date 2021-08-10
 */
@Component("checkLoginInterceptor")
public class CheckLoginInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckLoginInterceptor.class);

    /**
     * 前置拦截器
     *
     * @param request  请求
     * @param response 输出
     * @param handler  handler
     * @return 是否通过
     * @throws Exception 无
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        // 优先处理Cookie的用户信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("loginUser".equals(cookie.getName())) {
                    String user = cookie.getValue();
                    LoginUser loginUser = LoginUser.decode(user);
                    if (loginUser != null) {
                        LoginUserHolder.setLoginUser(loginUser);
                        return true;
                    }
                }
            }
        }

        // 处理Session的用户信息
        Object user = request.getSession().getAttribute("loginUser");
        if (user instanceof LoginUser) {
            LoginUser loginUser = (LoginUser) user;
            if (loginUser.getUserId() > 0) {
                LoginUserHolder.setLoginUser(loginUser);
                return true;
            }
        }

        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
            // ajax请求结果返回
            ResponseView<String> responseView = new ResponseView<>(302, "登录超时,请重新登录.");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            SerializeConfig config = new SerializeConfig();
            config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

            String result = JSON.toJSONString(responseView, config);
            response.getWriter().print(result);
        } else {
            // 页面请求结果返回
            response.sendRedirect(request.getContextPath() + "/plan/login/index");
        }
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
        Exception ex) throws Exception {
        LoginUserHolder.remove();
    }
}
