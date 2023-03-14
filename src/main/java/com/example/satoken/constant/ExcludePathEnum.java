package com.example.satoken.constant;

import lombok.Getter;

/**
 * 要排除登录校验的接口地址枚举类
 */
@Getter
public enum ExcludePathEnum {
    TEMP_TOKEN("/tempToken", "获取临时token的接口"),
    VERIFY_IMG("/getVerifyImg", "获取验证码图片的接口"),
    LOGIN("/login", "登录接口");

    private final String path;
    private final String remark;

    ExcludePathEnum(String path, String remark) {
        this.path = path;
        this.remark = remark;
    }
}
