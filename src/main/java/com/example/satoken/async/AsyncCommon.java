package com.example.satoken.async;

import com.example.satoken.annotation.Operation;
import com.example.satoken.constant.LoginConstant;
import com.example.satoken.entity.SystemOperateLog;
import com.example.satoken.service.SystemOperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
@Async
public class AsyncCommon {

    @Resource
    private SystemOperateLogService systemOperateLogService;

    /**
     * 异步保存操作日志
     * @param requestParams 请求参数
     * @param ip 操作人ip地址
     * @param operation 操作日志注解
     * @param source 操作数据来源
     * @param userInfo 当前登录人员信息
     * @param operateDate 操作时间
     */
    public void saveOperateLog(String requestParams, String ip, Operation operation, String source, Map<String, String> userInfo, Date operateDate){
        SystemOperateLog operateLog = new SystemOperateLog();
        operateLog.setOperateContent(StringUtils.isBlank(requestParams) ? "" : requestParams);
        operateLog.setOperateType(operation.type().getTypeCode());
        operateLog.setOperateSource(source);
        operateLog.setOperateTime(operateDate);
        operateLog.setOperateLoginName(userInfo.get(LoginConstant.LOGIN_NAME));
        operateLog.setOperateUserId(userInfo.get(LoginConstant.USER_ID));
        operateLog.setOperateUserName(userInfo.get(LoginConstant.REAL_NAME));
        operateLog.setUserIp(ip);
        operateLog.setOperateModuleId(operation.module().getModuleId());
        operateLog.setOperateModuleName(operation.module().getModuleName());
        operateLog.setLogType(operation.isLogin() ? "2" : "1");
        systemOperateLogService.save(operateLog);
    }
}
