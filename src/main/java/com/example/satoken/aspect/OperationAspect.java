package com.example.satoken.aspect;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.satoken.annotation.Operation;
import com.example.satoken.async.AsyncCommon;
import com.example.satoken.constant.LoginConstant;
import com.example.satoken.dto.BaseDto;
import com.example.satoken.util.ContextUtil;
import com.example.satoken.util.Sm4Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

@Aspect
@Component
@Slf4j
public class OperationAspect {

    @Resource
    private AsyncCommon asyncCommon;

    @Resource
    private Sm4Util sm4Util;

    /**
     * 配置切点
     */
    @Pointcut("execution(public * com.example.satoken..*Controller.*(..))")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
        HttpServletRequest request = requestAttributes.getRequest();
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        //这一步获取到的方法有可能是代理方法也有可能是真实方法
        Method m = ((MethodSignature) joinPoint.getSignature()).getMethod();
        //判断代理对象本身是否是连接点所在的目标对象，不是的话就要通过反射重新获取真实方法
        if (joinPoint.getThis().getClass() != joinPoint.getTarget().getClass()) {
            m = ReflectUtil.getMethod(joinPoint.getTarget().getClass(), m.getName(), m.getParameterTypes());
        }
        /*
        如果接口请求参数不为空并且被封装为类，并继承了BaseDto，则需要解密
         */
        boolean isSecret = Objects.nonNull(args) && args.length > 0 && Objects.nonNull(args[0])
                && args[0] instanceof BaseDto;
        Pair<String, String> pair = getParams(m, args, isSecret);
        String params = pair.getLeft();
        String requestPath = request.getRequestURI().substring(request.getContextPath().length());
        String requestMethod = request.getMethod();
        log.info("【{}】【{}】【请求参数】：{}", requestPath, requestMethod, params);
        // 执行方法获取返回值
        Object proceed = joinPoint.proceed(args);
        String responseStr = JSON.toJSONString(proceed);
        Map<String, String> userInfo = getLoginUserInfo();
        log.info("【{}】【{}】【操作用户】：{}\r\n【返回】 ======> {}", requestPath, requestMethod, userInfo, responseStr);
        // 异步保存操作日志
        Operation operation = m.getAnnotation(Operation.class);
        if (operation != null){
            String requestIP = ServletUtil.getClientIP(request);
            asyncCommon.saveOperateLog(params, requestIP, operation, pair.getRight(), userInfo, new Date());
        }
        return proceed;
    }

    /**
     * 获取当前登录人员的信息
     *
     * @return 当前登录人员的信息
     */
    private Map<String, String> getLoginUserInfo() {
        Map<String, String> map = new LinkedHashMap<>();
        String loginName;
        String userName;
        String loginUserId = "";
        try {
            userName = ContextUtil.getRealName();
            loginName = ContextUtil.getLoginName();
            loginUserId = ContextUtil.getUserId();
        } catch (Exception e) {
            loginName = "未登录用户";
            userName = "账号为空";
        }
        map.put(LoginConstant.LOGIN_NAME, loginName);
        map.put(LoginConstant.REAL_NAME, userName);
        map.put(LoginConstant.USER_ID, loginUserId);
        return map;
    }

    /**
     * 获取参数
     *
     * @param m        请求的方法
     * @param args     请求参数
     * @param isSecret 是否是加密的参数
     * @return 左边：json字符串形式的请求参数；右边：数据来源
     */
    private Pair<String, String> getParams(Method m, Object[] args, boolean isSecret) {
        if (isSecret) {
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(args[0]));
            // 获取加密值
            String encryptStr = jsonObject.getString("encryptStr");
            String decryptStr = sm4Util.decryptStr(encryptStr);
            args[0] = JSONObject.parseObject(decryptStr, args[0].getClass());
            return Pair.of(decryptStr, jsonObject.getString("source"));
        } else {
            //通过真实方法获取该方法的参数名称
            LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
            String[] parameterNames = paramNames.getParameterNames(m);
            //将参数名称与入参值一一对应起来
            Map<String, Object> params = new HashMap<>();
            if (parameterNames != null && parameterNames.length > 0) {
                for (int i = 0; i < parameterNames.length; i++) {
                    //这里加一个判断，如果使用requestParam接受参数，加了require=false，这里会存现不存在的现象
                    if (args[i] == null) {
                        continue;
                    }
                    //通过所在类转换，获取值，包含各种封装类都可以
                    ObjectMapper objectMapper = new ObjectMapper();
                    Object o = null;
                    try {
                        o = objectMapper.convertValue(args[i], args[i].getClass());
                    } catch (Exception e) {
                        log.error("获取参数值出错:" + parameterNames[i]);
                        o = "";
                    }
                    params.put(parameterNames[i], JSON.toJSON(o));
                }
            }
            String source = CollectionUtils.isEmpty(params) ? null : (String) params.get("source");
            return Pair.of(CollectionUtils.isEmpty(params) ? null : JSON.toJSONString(params), source);
        }
    }
}
