package com.example.satoken.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("system_operate_log")
public class SystemOperateLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 操作人id
     */
    @TableField("operate_user_id")
    private String operateUserId;

    /**
     * 操作人名称
     */
    @TableField("operate_user_name")
    private String operateUserName;

    /**
     * 操作人账号
     */
    @TableField("operate_login_name")
    private String operateLoginName;

    /**
     * 操作类型
     */
    @TableField("operate_type")
    private String operateType;

    /**
     * 操作模块id
     */
    @TableField("operate_module_id")
    private String operateModuleId;

    /**
     * 操作内容，请求参数
     */
    @TableField("operate_content")
    private String operateContent;

    /**
     * 操作时间
     */
    @TableField("operate_time")
    private Date operateTime;

    /**
     * 操作人ip地址
     */
    @TableField("user_ip")
    private String userIp;

    /**
     * 操作模块名称
     */
    @TableField("operate_module_name")
    private String operateModuleName;

    /**
     * 数据来源 1、WEB端；2、app端
     */
    @TableField("operate_source")
    private String operateSource;

    /**
     * 类型，1、操作日志；2、登录日志
     */
    @TableField("log_type")
    private String logType;
}
