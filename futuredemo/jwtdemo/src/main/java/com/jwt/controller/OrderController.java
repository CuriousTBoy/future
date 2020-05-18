package com.jwt.controller;

import com.jwt.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Tanyu
 * @Date 2020/5/14 14:26
 * @Version 1.0
 */
@RestController
public class OrderController {

  @Autowired
  OrderService orderService;

  @PostMapping("/getserialnumber")
  public String getSerialNumber(){
    return orderService.getSerialNumber();
  }


  @PostMapping("/getserialkey")
  public String getSerialKey(){
    return orderService.getSerialKey();
  }
}
