package com.zzh.controller;

import com.zzh.common.Result;
import com.zzh.pojo.User;
import com.zzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 注册
    // 参数合法性校验：用户名和密码均必须为5-16位的非空字符
    @PostMapping("/register")
    public Result register(String username, String password) {
        // 查询用户是否存在
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已被占用");
        }
        // 注册新用户
        userService.registerUser(username, password);
        return Result.success();
    }

}
