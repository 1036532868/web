package com.example.user.service;

import com.example.exception.CRUDException;
import com.example.userApi.domain.User;

/**
 * @author gong_da_kai
 * @version 1.0.0
 * @date 2021/2/24 18:30
 * @since 1.0.0
 */
public interface UserService{


    User selectUser(String username, String password);

    User login(String username, String password) throws CRUDException;
}
