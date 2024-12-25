package com.zzh.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    // 让springmvc把当前对象转换成json字符串时，忽略password属性，最后返回的data中就不会包含该属性
    @JsonIgnore
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