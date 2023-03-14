package com.example.satoken.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    /**
     * 账号
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String verifyCode;

    /**
     * 临时token，用来校验验证码
     */
    private String tempToken;
}
