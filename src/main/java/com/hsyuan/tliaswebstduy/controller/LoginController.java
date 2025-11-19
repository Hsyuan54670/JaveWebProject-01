package com.hsyuan.tliaswebstduy.controller;

import com.hsyuan.tliaswebstduy.pojo.Emp;
import com.hsyuan.tliaswebstduy.pojo.LoginInfo;
import com.hsyuan.tliaswebstduy.pojo.Result;
import com.hsyuan.tliaswebstduy.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private EmpService empService;
    @PostMapping
    public Result login(@RequestBody Emp emp) {
        log.info("登录请求参数: {}", emp);
        LoginInfo loginInfo = empService.LoginInfo(emp);
        if(loginInfo != null) {
            return Result.success(loginInfo);
        }
        return Result.error("用户名或密码错误");
    }
}
