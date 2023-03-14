package com.example.satoken.util;

import cn.dev33.satoken.stp.StpUtil;
import com.example.satoken.constant.LoginConstant;

/**
 * 获取上下文数据
 */
public class ContextUtil {

    /**
     * 获取当前登录账号
     * @return 当前登录账号
     */
    public static String getLoginName() {
        return StpUtil.isLogin() ? StpUtil.getSession().getString(LoginConstant.LOGIN_NAME) : "未登录用户";
    }

    /**
     * 获取当前登录人员姓名
     * @return 当前登录人员姓名
     */
    public static String getRealName(){
        return StpUtil.isLogin() ? StpUtil.getSession().getString(LoginConstant.REAL_NAME) : "未登录用户";
    }

    /**
     * 获取当前登录人员id
     * @return 当前登录人员id
     */
    public static String getUserId(){
        return StpUtil.isLogin() ? StpUtil.getSession().getString(LoginConstant.USER_ID) : "未登录用户";
    }
}
