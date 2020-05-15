package com.hans.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author Hansing
 * @TODO: 2020/5/6 用户实体类
 */
@Data
public class User {

    @TableId(value = "userid")
    private String userid;      //用户名
    private String password;    //密码
    private String nickname;    //昵称
    private int sex;            //性别
    private int age;            //年龄
    private String profilehead; //头像
    private String profile;     //简介
    private String firsttime;   //注册时间
    private String lasttime;    //最后登录时间
    private int status;      //账号状态(1正常 0禁用)

}
