package com.example.satoken.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.example.satoken.constant.SaTokenConstant;
import com.example.satoken.constant.TokenStyleEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handler -> {
            StpUtil.checkLogin();
            // 这里可以给每个接口地址的路径鉴权
        })).addPathPatterns(SaTokenConstant.allRouters)
                .excludePathPatterns(SaTokenConstant.excludePathPatterns);
    }

    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary(){
        SaTokenConfig config = new SaTokenConfig();
        // token名称
        config.setTokenName(SaTokenConstant.TOKEN_NAME);
        // token有效期，单位 秒，默认是30天
        config.setTimeout(30 * 24 * 60 * 60);
        // token无操作存活时间(指定时间内无操作就视为token过期) 单位：秒
        config.setActivityTimeout(SaTokenConstant.ACTIVITY_TIMEOUT);
        // 是否允许同一账号并发登录，true是允许多个同一账号一起登录，false时同一账号新登录的会挤掉旧登录的
        config.setIsConcurrent(false);
        // 在多人登录同一账号时，是否共用一个token
        config.setIsShare(true);
        // token风格
        config.setTokenStyle(TokenStyleEnum.SIMPLE_UUID.getTokenStyle());
        // 是否输出操作日志
        config.setIsLog(false);
        /*
         是否尝试从 cookie 里读取 Token，此值为 false 后，StpUtil.login(id) 登录时也不会再往前端注入Cookie，
         false时，可以返回给前端token，然后让前端在每次请求时header里携带返回的token，
         这样可以用于不能使用cookie的设备端，不在局限于web浏览器端
         */
        config.setIsReadCookie(false);
        return config;
    }
}
