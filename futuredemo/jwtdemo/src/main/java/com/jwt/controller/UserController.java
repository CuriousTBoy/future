package com.jwt.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.annotations.UserLoginToken;
import com.jwt.bean.User;
import com.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Tanyu
 * @Date 2020/5/14 10:44
 * @Version 1.0
 */
@RestController
public class UserController {

  @Autowired
  UserService userService;

  @GetMapping("/test01")
  public String test01() {
    return "不需要token";
  }

  @UserLoginToken
  @GetMapping("/test02")
  public String test02() {
    return "需要token";
  }


  @PostMapping("/gettoken")
  public String getToken(User user) {
    String token = "";
    token = JWT.create().withAudience(user.getId())
        .sign(Algorithm.HMAC256(user.getPassword()));
    return token;
  }

  @UserLoginToken
  @PostMapping("/login")
  public User login(User user){
    User userById = userService.findUserById(user.getId());
    return userById;
  }
}
