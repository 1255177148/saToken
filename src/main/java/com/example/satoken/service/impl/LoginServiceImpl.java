package com.example.satoken.service.impl;

import com.example.satoken.constant.LoginConstant;
import com.example.satoken.dto.LoginDto;
import com.example.satoken.model.BaseResult;
import com.example.satoken.service.LoginService;
import com.example.satoken.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisUtil redisUtil;

    private static final String tempTokenPre = "tempToken";

    @Override
    public BaseResult<String> checkLogin(LoginDto dto) {
        if (StringUtils.isBlank(dto.getTempToken())) {
            return BaseResult.fail(530, LoginConstant.TOKEN_EMPTY);
        }
        if (StringUtils.isBlank(dto.getVerifyCode())) {
            return BaseResult.fail(530, LoginConstant.VERIFY_CODE_EMPTY);
        }
        if (StringUtils.isBlank(dto.getLoginName())) {
            return BaseResult.fail(530, LoginConstant.LOGIN_NAME_EMPTY);
        }
        if (StringUtils.isBlank(dto.getPassword())) {
            return BaseResult.fail(530, LoginConstant.PASSWORD_EMPTY);
        }
        // 校验
        Object verifyCode = redisUtil.get(dto.getTempToken());
        if (verifyCode == null) {
            return BaseResult.fail(530, LoginConstant.VERIFY_OVERDUE);
        }
        if (!dto.getVerifyCode().equalsIgnoreCase(verifyCode.toString())) {
            return BaseResult.fail(530, LoginConstant.VERIFY_CODE_ERROR);
        }
        if (!"admin".equals(dto.getLoginName())) {
            return BaseResult.fail(530, LoginConstant.LOGIN_NAME_ERROR);
        }
        if (!"123".equals(dto.getPassword())) {
            return BaseResult.fail(530, LoginConstant.PASSWORD_ERROR);
        }
        // 校验成功后，移除redis
        redisUtil.delete(dto.getTempToken());
        return BaseResult.success(null, null);
    }

    @Override
    public void saveTempToken(String tempToken) {
        redisUtil.set(tempTokenPre + tempToken, "1", 300);
    }

    @Override
    public void deleteTempToken(String tempToken) {
        redisUtil.delete(tempTokenPre + tempToken);
    }

    @Override
    public boolean checkTempToken(String tempToken) {
        Object o = redisUtil.get(tempTokenPre + tempToken);
        return o != null;
    }
}
