package com.hans.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hans.entity.User;
import com.hans.mapper.UserMapper;
import com.hans.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author hansing
 * @TODO: 2020/5/7
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserByUserid(String userid) {
        return baseMapper.selectById(userid);
    }

    @Override
    public int updateUser(User user) {
        return baseMapper.updateById(user);
    }
}
