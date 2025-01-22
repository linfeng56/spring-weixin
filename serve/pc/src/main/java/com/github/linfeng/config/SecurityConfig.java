package com.github.linfeng.config;

import com.github.linfeng.plan.interceptor.CheckLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置，基于 JWT 的无状态认证。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                         JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            // 关闭 CSRF，因为我们使用 JWT
            .csrf().disable()
            // 统一处理未认证的请求
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
            // 使用无状态会话，所有请求都需要携带 token
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // 允许登录接口匿名访问，其他接口需要认证
            .authorizeRequests()
                .antMatchers("/api/login", "/h2-console/**").permitAll()
                .anyRequest().authenticated();

        // 添加 JWT 过滤器，在 UsernamePasswordAuthenticationFilter 之前执行
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // 如果需要自定义 AuthenticationManager，可在此暴露 Bean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
