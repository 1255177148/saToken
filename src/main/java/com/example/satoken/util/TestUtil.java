package com.example.satoken.util;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.fastjson.JSON;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.util.HashMap;
import java.util.Map;

public class TestUtil {

    public static void main(String[] args){
        byte[] key = ByteUtils.fromHexString("6d476239336e624a6966383135346551");
        SymmetricCrypto sm4 = SmUtil.sm4(key);

        Map<String, Object> map = new HashMap<>();
        map.put("userName", "admin");
        String str = sm4.encryptHex(JSON.toJSONString(map));
        System.out.println(str);
    }
}
