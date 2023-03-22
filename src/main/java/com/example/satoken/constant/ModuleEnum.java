package com.example.satoken.constant;

import lombok.Getter;

/**
 * 菜单模块枚举类，真实场景下菜单模块可以维护在数据库字典表里
 */
@Getter
public enum ModuleEnum {
    USER("1", "用户管理"),
    ROLE("2", "角色设置");

    private final String moduleId;
    private final String moduleName;

    ModuleEnum(String moduleId, String moduleName) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
    }

    public static String getModuleNameById(String moduleId){
        ModuleEnum[] enums = ModuleEnum.values();
        for (ModuleEnum moduleEnum : enums){
            if (moduleEnum.getModuleId().equals(moduleId)){
                return moduleEnum.getModuleName();
            }
        }
        return null;
    }
}
