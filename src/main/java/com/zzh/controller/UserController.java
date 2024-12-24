package com.zzh.controller;

import com.zzh.common.Result;
import com.zzh.pojo.User;
import com.zzh.service.UserService;
import com.zzh.utils.JwtUtil;
import com.zzh.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.HashMap;


@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // 注册
    // 参数合法性校验：用户名和密码均必须为5-16位的非空字符
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 查询用户是否存在
        User user = userService.findByUserName(username);
        if (user != null) {
            return Result.error("用户名已被占用");
        }
        // 注册新用户
        userService.registerUser(username, password);
        return Result.success();
    }

    // 登录
    // 参数合法性校验：用户名和密码均必须为5-16位的非空字符
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}$") String username,
                        @Pattern(regexp = "^\\S{5,16}$") String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        if (loginUser != null) {
            // 判断密码是否正确
            if (loginUser.getPassword().equals(Md5Util.getMD5String(password))) {
                // 登录成功，对用户进行赋权，进行之后的操作则不需要登录，否则不能进行获取其他资源的操作
                HashMap<String, Object> claims = new HashMap<>();
                claims.put("id", loginUser.getId());
                claims.put("user", loginUser.getUsername());
                String token = JwtUtil.genToken(claims);
                return Result.success(token);
            } else {
                return Result.error("密码错误");
            }
        }
        return Result.error("用户名不存在");
    }

}
