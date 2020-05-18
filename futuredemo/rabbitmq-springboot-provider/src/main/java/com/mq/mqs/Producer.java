package com.mq.mqs;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author Tanyu
 * @Date 2020/5/15 16:19
 * @Description
 * @Version 1.0
 */
@Component
public class Producer {

  private static final Logger logger = LoggerFactory.getLogger(Producer.class);

  private static final String EXCHANGE_NAME = "message_confirm_exchange";

  private static final String ROUTING_KEY = "user.";

  @Autowired
  RabbitTemplate rabbitTemplate;

  public void sendMessage() {
    for (int i = 1; i <= 3; i++) {
      //消息的UUID 唯一标识
      CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
      logger.info("【Producer】发送的消费ID = {}", correlationData.getId());
      String msg = "hello confirm message----->" + i;
      logger.info("【Producer】发送的消息 = {}", msg);
      rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, msg, correlationData);
    }
  }
}
