package com.example.satoken.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.satoken.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @SaCheckPermission("user:add")
    @GetMapping("/user")
    public BaseResult<String> getUserInfo(){
        return BaseResult.success("用户信息", null);
    }
}
