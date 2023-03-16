package com.example.satoken.constant;

public class LoginConstant {
    public static final String TOKEN_EMPTY = "token不能为空！";
    public static final String LOGIN_NAME_EMPTY = "请输入账号！";
    public static final String PASSWORD_EMPTY = "请输入密码！";
    public static final String LOGIN_NAME_ERROR = "账号不存在！";
    public static final String PASSWORD_ERROR = "密码错误，请重新输入！";
    public static final String VERIFY_CODE_EMPTY = "验证码不能为空！";
    public static final String VERIFY_CODE_ERROR = "验证码错误，请重新输入！";
    public static final String VERIFY_OVERDUE = "验证码已过期，请重新登录！";
    public static final String CHECK_ERROR = "密码输入错误次数超过5次，请5分钟后再试！";

    public static final String LOGIN_NAME = "loginName";
    public static final String REAL_NAME = "userName";
    public static final String USER_ID = "userId";

    /**
     * 用于存放登录时密码验证错误次数缓存的key
     */
    public static final String CHECK_ERROR_KEY_PRE = "loginCheck";
}
