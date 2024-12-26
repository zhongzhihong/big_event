package com.zzh.service.impl;

import com.zzh.mapper.UserMapper;
import com.zzh.pojo.User;
import com.zzh.service.UserService;
import com.zzh.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void registerUser(String username, String password) {
        // 对密码进行加密
        String md5String = Md5Util.getMD5String(password);
        // 调用方法添加用户
        userMapper.add(username, md5String);
    }

    @Override
    public void update(User user) {
        // 更新修改时间
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

}
