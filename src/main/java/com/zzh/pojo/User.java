package com.zzh.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 主键ID
    private Integer id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 昵称
    private String nickname;
    // 邮箱
    private String email;
    // 用户头像地址
    private String userPic;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}