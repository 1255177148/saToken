package com.example.satoken.exception;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
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

    /**
     * 全局权限校验异常处理
     * @param exception 权限校验异常
     * @return
     */
    @ExceptionHandler(NotPermissionException.class)
    public BaseResult<String> handlerNotPermissionException(NotPermissionException exception){
        // 打印日志
        log.error(exception.getMessage(), exception);
        return BaseResult.fail(520, SaTokenConstant.PERMISSION_ERROR);
    }

    /**
     * 全局账号封禁异常处理
     * @param exception 账号封禁异常
     * @return
     */
    @ExceptionHandler(DisableServiceException.class)
    public BaseResult<String> handlerDisableServiceException(DisableServiceException exception){
        // 打印日志
        log.error(exception.getMessage(), exception);
        return BaseResult.fail(520, SaTokenConstant.LOGIN_DISABLE);
    }
}
