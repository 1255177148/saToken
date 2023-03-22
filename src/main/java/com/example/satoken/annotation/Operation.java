package com.example.satoken.annotation;

import com.example.satoken.constant.ModuleEnum;
import com.example.satoken.constant.OperateTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解，有此注解的会被记录日志
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Operation {

    /**
     * 操作类型
     * @return 操作类型的枚举
     */
    OperateTypeEnum type() default OperateTypeEnum.SELECT;

    /**
     * 是否是登录类型的操作
     * @return
     */
    boolean isLogin() default false;

    /**
     * 操作模块
     * @return
     */
    ModuleEnum module() default ModuleEnum.USER;
}
