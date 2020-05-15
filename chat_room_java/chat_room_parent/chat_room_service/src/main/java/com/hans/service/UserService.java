package com.hans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hans.entity.User;


/**
 * @author hansing
 * @TODO: 2020/5/7
 */
public interface UserService extends IService<User> {

    User getUserByUserid(String userid);

    int updateUser(User user);
}
