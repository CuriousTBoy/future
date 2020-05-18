package com.jwt.service.impl;

import com.jwt.bean.User;
import com.jwt.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author Tanyu
 * @Date 2020/5/14 10:04
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

  @Override
  public User findUserById(String id) {
    return new User("1","admin","123456");
  }
}
