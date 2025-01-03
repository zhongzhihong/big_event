package com.zzh.controller;

import com.zzh.common.Result;
import com.zzh.pojo.User;
import com.zzh.service.UserService;
import com.zzh.utils.JwtUtil;
import com.zzh.utils.Md5Util;
import com.zzh.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;


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

    // 返回当前用户的详细信息【但是不能返回用户的密码！！】
    @GetMapping("/userInfo")
    public Result<User> userInfo(/*@RequestHeader(name = "Authorization") String token*/) {
        // 根据用户名查询用户，这里的用户名是登录系统的用户，而登录用户可以从token中获取，因为在登录的时候，将用户名保存进了token中
        // Map<String, Object> claims = JwtUtil.parseToken(token);
        // String loginUser = (String) claims.get("user");
        // 使用ThreadLocal优化
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("user");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    // 更新逻辑中也需要对参数进行合法性校验，这时需要去实体类中对实体属性添加注解，最后还要在请求时对实体类添加 @Validated 注解
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

}
