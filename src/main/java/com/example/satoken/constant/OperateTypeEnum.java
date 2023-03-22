package com.example.satoken.constant;

import lombok.Getter;

@Getter
public enum OperateTypeEnum {
    SELECT("0", "查询"),
    INSERT("1", "新增"),
    UPDATE("2", "编辑"),
    DELETE("3", "删除"),
    IMPORT("4", "导入"),
    EXPORT("5", "导出"),
    LOGIN("6", "登录");


    private final String typeCode;

    private final String typeName;

    OperateTypeEnum(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }
}
