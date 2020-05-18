package com.jwt.service;

import com.jwt.bean.User;

/**
 * @Author Tanyu
 * @Date 2020/5/14 10:03
 * @Version 1.0
 */
public interface UserService {

  User findUserById(String id);
}
