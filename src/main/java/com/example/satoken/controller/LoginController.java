package com.example.satoken.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.temp.SaTempUtil;
import com.alibaba.fastjson.JSON;
import com.example.satoken.annotation.Operation;
import com.example.satoken.constant.LoginConstant;
import com.example.satoken.constant.ModuleEnum;
import com.example.satoken.constant.OperateTypeEnum;
import com.example.satoken.dto.LoginDto;
import com.example.satoken.entity.User;
import com.example.satoken.model.BaseResult;
import com.example.satoken.service.LoginService;
import com.example.satoken.util.VerifyImgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@Slf4j
public class LoginController {

    @Resource
    private VerifyImgUtil verifyImgUtil;

    @Resource
    private LoginService loginService;

    /**
     * 在获取验证码接口和登录接口前，先调用，获取一个临时token，用来作为验证码的key
     *
     * @return
     */
    @GetMapping("tempToken")
    public BaseResult<String> getTempToken(@RequestParam("tokenKey") String tokenKey) {
        log.info("开始获取临时token------>" + tokenKey);
        String token = SaTempUtil.createToken(tokenKey, 5 * 60);
        loginService.saveTempToken(token);
        return BaseResult.success(token, null);
    }

    /**
     * 获取图片验证码
     *
     * @param tempToken 获取到的临时token
     * @param response
     */
    @GetMapping("getVerifyImg")
    public BaseResult<String> getVerifyImg(@RequestParam("tempToken") String tempToken, HttpServletResponse response) throws IOException {
        log.info("开始获取图片验证码------>" + tempToken);
        if (!loginService.checkTempToken(tempToken)) {
            return BaseResult.fail(530, LoginConstant.TOKEN_EMPTY);
        }
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream os = response.getOutputStream();
        verifyImgUtil.lineCaptcha(os, tempToken);
        try {
            os.flush();
        } finally {
            os.close();
        }
        return null;
    }

    /**
     * 模拟登录接口，真实场景中，可以先提供一个接口，返回一个临时token，
     * 然后再提供一个获取验证码的接口，里面需要传入刚才返回的临时token，生成验证码后，以临时token作为key，验证码作为value放入缓存，
     * 然后再来调用这个正式登录接口，里面需要传验证码和临时token以及用户的账号密码，先根据临时token从缓存中获取验证码来比对，
     * 然后再比对账号密码，比对成功后，删除缓存中对应临时token的验证码数据，并删除临时token
     *
     * @param dto 登录数据
     * @return
     */
    @PostMapping("/login")
    @Operation(type = OperateTypeEnum.LOGIN, isLogin = true, module = ModuleEnum.USER)
    public BaseResult<Object> login(@RequestBody LoginDto dto) {
        log.info("开始登录------>" + JSON.toJSONString(dto));
        // 校验是否封禁
        StpUtil.checkDisable("10001");
        BaseResult<Object> result = loginService.checkLogin(dto);
        if (!result.isState()) {
            return result;
        }
        loginService.deleteTempToken(dto.getTempToken());// 校验成功后移除临时token
        // 登录
        User user = (User) result.getData();
        StpUtil.login(user.getId());
        // 将用户的数据放入上下文session中
        StpUtil.getSession().set(LoginConstant.LOGIN_NAME, user.getLoginName());
        StpUtil.getSession().set(LoginConstant.REAL_NAME, user.getUserName());
        StpUtil.getSession().set(LoginConstant.USER_ID, user.getId());
        return BaseResult.success(StpUtil.getTokenValue(), "登录成功");
    }

    /**
     * 注销登录接口
     */
    @PostMapping("logout")
    public void logout(){
        StpUtil.logout();
    }
}
