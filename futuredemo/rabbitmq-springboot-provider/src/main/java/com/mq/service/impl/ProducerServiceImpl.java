package com.mq.service.impl;

import com.mq.mqs.Producer;
import com.mq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Tanyu
 * @Date 2020/5/15 16:12
 * @Description
 * @Version 1.0
 */
@Service
public class ProducerServiceImpl implements ProducerService {

  @Autowired
  Producer producer;
  @Override
  public String sendMessage() {
    producer.sendMessage();
    return "success";
  }
}
