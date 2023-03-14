package com.example.satoken.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.example.satoken.constant.SaTokenConstant;
import com.example.satoken.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局登录异常处理
     * @param exception 登录抛出的异常
     * @return 提示信息
     */
    @ExceptionHandler(NotLoginException.class)
    public BaseResult<Object> handlerNotLoginException(NotLoginException exception) {
        // 打印日志
        log.error(exception.getMessage(), exception);
        // 判断登录异常的场景，定制化返回提示
        String type = exception.getType();
        switch (type){
            case NotLoginException.INVALID_TOKEN:
            case NotLoginException.TOKEN_TIMEOUT:
                return BaseResult.fail(520, SaTokenConstant.TOKEN_OVERDUE);
            case NotLoginException.BE_REPLACED:
            case NotLoginException.KICK_OUT:
                return BaseResult.fail(520, SaTokenConstant.LOGIN_REPLACE);
            default:
                return BaseResult.fail(520, SaTokenConstant.NOT_TOKEN);
        }
    }
}
