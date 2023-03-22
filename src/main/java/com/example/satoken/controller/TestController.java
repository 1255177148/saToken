package com.example.satoken.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.example.satoken.annotation.Operation;
import com.example.satoken.constant.ModuleEnum;
import com.example.satoken.constant.OperateTypeEnum;
import com.example.satoken.dto.TestDto;
import com.example.satoken.model.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @SaCheckPermission("user:select")
    @Operation(type = OperateTypeEnum.SELECT, module = ModuleEnum.USER)
    @GetMapping("/user")
    public BaseResult<String> getUserInfo(TestDto dto){
        return BaseResult.success("用户信息:" + dto.getUserName(), null);
    }

    @SaCheckPermission("user:select")
    @Operation(type = OperateTypeEnum.SELECT, module = ModuleEnum.USER)
    @GetMapping("/userDetail")
    public BaseResult<String> getUserDetail(String userName){
        return BaseResult.success("用户信息:" + userName, null);
    }
}
