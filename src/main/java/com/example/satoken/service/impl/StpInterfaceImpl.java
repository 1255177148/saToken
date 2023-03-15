package com.example.satoken.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {

    /**
     * sa-token框架获取当前登录人员权限的接口
     * @param loginId StpUtil.login(loginId)中的loginId
     * @param loginType 多账号体系标识，目前用不到
     * @return 权限集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> permissions = new ArrayList<>();
        permissions.add("user:select");
        return permissions;
    }

    /**
     * sa-token框架获取当前登录人员角色的接口
     * @param loginId StpUtil.login(loginId)中的loginId
     * @param loginType 多账号体系标识，目前用不到
     * @return 角色集合
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
