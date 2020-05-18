package com.mq.mqs;

import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author Tanyu
 * @Date 2020/5/15 16:23
 * @Description
 * @Version 1.0
 */
public class CustomConfirmAndReturnCallback implements RabbitTemplate.ReturnCallback,RabbitTemplate.ConfirmCallback {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  //日志输出工具
  private static final Logger logger = LoggerFactory.getLogger(CustomConfirmAndReturnCallback.class);


  /**
   *PostConstruct: 用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化.
   */
  @PostConstruct
  public void init(){
    //指定 ConfirmCallback
    rabbitTemplate.setConfirmCallback(this);
    //指定 ReturnCallback
    rabbitTemplate.setReturnCallback(this);
  }

  /**
   * 消息从交换机成功到达队列，则returnedMessage方法不会执行;
   * 消息从交换机未能成功到达队列，则returnedMessage方法会执行;
   */
  @Override
  public void returnedMessage(Message message, int i, String s, String s1, String s2) {
    logger.info("returnedMessage回调方法被调用啦，有消息没有正常到达队列>>>" + new String(message.getBody(), StandardCharsets.UTF_8) + ",replyCode:" + i + ",replyText:" + s + ",exchange:" + s1 + ",routingKey:" + s2);
    //补偿性措施！
  }

  /**
   * 如果消息没有到达交换机,则该方法中isSendSuccess = false,error为错误信息;
   * 如果消息正确到达交换机,则该方法中isSendSuccess = true;
   */
  @Override
  public void confirm(CorrelationData correlationData, boolean b, String s) {
    logger.info("confirm回调方法>>>回调消息ID为: " + correlationData.getId());
    if (b) {
      logger.info("confirm回调方法>>>消息发送到交换机成功！");
    } else {
      logger.info("confirm回调方法>>>消息发送到交换机失败！，原因 : [{}]", s);
      //补偿性措施！
    }
  }
}
