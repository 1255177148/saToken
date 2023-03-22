package com.example.satoken.util;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 国密 sm4 加解密工具类
 */
@Component
public class Sm4Util {

    @Value("${encrypt.key}")
    private String encryptKey;

    /**
     * 解密
     * @param encryptStr 加密的字符串
     * @return
     */
    public String decryptStr(String encryptStr){
        byte[] key = ByteUtils.fromHexString(encryptKey);
        SymmetricCrypto sm4 = SmUtil.sm4(key);
        return sm4.decryptStr(encryptStr);
    }

    public String encryptStr(String str){
        byte[] key = ByteUtils.fromHexString(encryptKey);
        SymmetricCrypto sm4 = SmUtil.sm4(key);
        return sm4.encryptHex(str);
    }
}
