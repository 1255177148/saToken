package com.example.satoken.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.satoken.constant.LoginConstant;
import com.example.satoken.dto.LoginDto;
import com.example.satoken.entity.User;
import com.example.satoken.mapper.UserMapper;
import com.example.satoken.model.BaseResult;
import com.example.satoken.service.LoginService;
import com.example.satoken.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;

    @Value("${check.error-num}")
    private Integer checkErrorNum;

    private static final String tempTokenPre = "tempToken";

    @Override
    public BaseResult<Object> checkLogin(LoginDto dto) {
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
        // 根据用户名获取下用户信息
        LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(User::getLoginName, dto.getLoginName());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return BaseResult.fail(530, LoginConstant.LOGIN_NAME_ERROR);
        }
        // 校验是否封禁
        StpUtil.checkDisable(user.getId());
        if (!user.getPassword().equals(dto.getPassword())) {
            String key = LoginConstant.CHECK_ERROR_KEY_PRE + dto.getLoginName();
            AtomicInteger atomicInteger = (AtomicInteger) redisUtil.get(key);
            if (atomicInteger == null){
                atomicInteger = new AtomicInteger(0);
            }
            int num = atomicInteger.incrementAndGet();
            redisUtil.set(key, atomicInteger);
            if (num >= checkErrorNum){
                StpUtil.disable(user.getId(), 300);
                return BaseResult.fail(530, LoginConstant.CHECK_ERROR);
            }
            return BaseResult.fail(530, LoginConstant.PASSWORD_ERROR);
        }
        // 校验成功后，移除redis
        redisUtil.delete(dto.getTempToken());
        return BaseResult.success(user, null);
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
