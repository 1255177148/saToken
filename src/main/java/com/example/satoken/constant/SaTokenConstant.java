package com.example.satoken.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SaTokenConstant {

    /**
     * token的字段名
     */
    public static final String TOKEN_NAME = "token";

    /**
     * token无操作存活时间(指定时间内无操作就视为token过期) 单位：秒
     */
    public static final long ACTIVITY_TIMEOUT = 24 * 60 * 60;

    public static final List<String> allRouters = Collections.singletonList("/**");

    public static final List<String> excludePathPatterns = Arrays.asList(
            "/ss/**",
            "/test/**",
            "/contract/**",
            "/common/tree/area/update",
            "/file/upload/img/head/**",
            "/swagger-resources/**",
            "/webjars/**",
            "/v2/**",
            "/doc.html",
            "**/swagger-ui.html",
            "/swagger-ui.html/**",
            "/img/head/**"
    );
}
