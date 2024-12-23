package com.zzh.service;

import com.zzh.pojo.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    User findByUserName(String username);

    void registerUser(String username, String password);
}
