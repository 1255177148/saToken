package com.example.satoken.dto;

import lombok.Data;

/**
 * 接口请求参数父类，如果请求参数要加密的话，请求参数都要继承此类
 */
@Data
public class BaseDto {

    /**
     * 加密内容密文
     */
    private String encryptStr;

    /**
     * 日志数据来源 1、web端；2、app端
     */
    private String source = "1";
}
