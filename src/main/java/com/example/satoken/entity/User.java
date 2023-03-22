package com.example.satoken.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("login_name")
    private String loginName;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;
}
