package com.example.user.service.impl;

import com.example.exception.CRUDException;
import com.example.user.mapper.UserMapper;
import com.example.user.service.UserService;
import com.example.userApi.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/24 18:31
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUser(String username, String password) {
        return userMapper.selectUser(username, password);
    }

    @Override
    public User login(String username, String password) throws CRUDException {
        User user = userMapper.selectUser(username, password);
        if (user == null){
            throw new CRUDException("账号或密码错误");
        }

        return user;
    }
}
