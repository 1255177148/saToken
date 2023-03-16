package com.example.satoken.service;

import com.example.satoken.dto.LoginDto;
import com.example.satoken.model.BaseResult;

public interface LoginService {

    /**
     * 登录校验
     * @param dto
     * @return
     */
    BaseResult<Object> checkLogin(LoginDto dto);

    /**
     * 存放临时token
     * @param tempToken 临时token
     */
    void saveTempToken(String tempToken);

    /**
     * 删除临时token
     * @param tempToken 临时token
     */
    void deleteTempToken(String tempToken);

    /**
     * 校验临时token是否有效
     * @param tempToken 临时token
     * @return
     */
    boolean checkTempToken(String tempToken);
}
