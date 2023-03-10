package com.example.satoken.constant;

import lombok.Getter;

@Getter
public enum TokenStyleEnum {
    /**
     * 一般的uuid风格
     */
    UUID("uuid"),
    /**
     * uuid风格，只不过去掉了中划线
     */
    SIMPLE_UUID("simple-uuid"),
    /**
     * 随机32位字符串
     */
    RANDOM_32("random-32"),
    /**
     * 随机64位字符串
     */
    RANDOM_64("random-64"),
    /**
     * 随机128位字符串
     */
    RANDOM_128("random-128"),
    /**
     * tik风格，gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__
     */
    TIK("tik");

    private final String tokenStyle;

    TokenStyleEnum(String tokenStyle) {
        this.tokenStyle = tokenStyle;
    }
}
