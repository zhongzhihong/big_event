package com.zzh.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 主键ID
    // @NotNull：值不能为null
    @NotNull
    private Integer id;
    // 用户名
    private String username;
    // 密码
    // 让springmvc把当前对象转换成json字符串时，忽略password属性，最后返回的data中就不会包含该属性
    @JsonIgnore
    private String password;
    // 昵称
    // @NotEmpty：值不能为null，且内容不能为空
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    // 邮箱
    @NotEmpty
    @Email
    private String email;
    // 用户头像地址
    private String userPic;
    // 创建时间
    private LocalDateTime createTime;
    // 更新时间
    private LocalDateTime updateTime;
}