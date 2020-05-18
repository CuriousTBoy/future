package com.jwt.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jwt.annotations.PassToken;
import com.jwt.annotations.UserLoginToken;
import com.jwt.bean.User;
import com.jwt.service.UserService;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author Tanyu
 * @Date 2020/5/14 10:01
 * @Version 1.0
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

  @Autowired
  UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String token=request.getHeader("token");
    if (!(handler instanceof HandlerMethod)){
      return true;
    }
    HandlerMethod handlerMethod= (HandlerMethod) handler;
    Method method=handlerMethod.getMethod();

    if (method.isAnnotationPresent(PassToken.class)){
      PassToken passToken = method.getAnnotation(PassToken.class);
      if (passToken.required()){
        return true;
      }
    }

    if (method.isAnnotationPresent(UserLoginToken.class)){
      UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
      if (userLoginToken.required()){
        if (token==null){
          throw new RuntimeException("无token，请重新登录");
        }

        String userId;
        try {
          userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException e) {
          throw new RuntimeException("401");
        }

        User user = userService.findUserById(userId);
//        User user1=new User("12","123","123");

        if (user==null){
          throw new RuntimeException("用户不存在，请重新登录");
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
          jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
          throw new RuntimeException("401");
        }
        return true;
      }
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {

  }
}
