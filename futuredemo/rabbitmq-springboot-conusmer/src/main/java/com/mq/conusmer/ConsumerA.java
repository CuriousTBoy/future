package com.mq.conusmer;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author Tanyu
 * @Date 2020/5/15 16:34
 * @Description
 * @Version 1.0
 */
@Component
public class ConsumerA {

  private static final Logger logger= LoggerFactory.getLogger(ConsumerA.class);

  @RabbitListener(queues = "message_confirm_queue")
  public void receiveMessage(String msg, Channel channel, Message message) throws IOException {

    try {
      // 这里模拟一个空指针异常，
      String string=null;
      string.length();


      logger.info("【ConsumerA成功接收到消息】>>> {}", msg);
      // 确认收到消息，只确认当前消费者的一个消息收到
      channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    } catch (IOException e) {
        if (message.getMessageProperties().getRedelivered()){
          logger.info("【ConsumerA】消息已经回滚过，拒绝接收消息 ： {}", msg);
          // 拒绝消息，并且不再重新进入队列
          channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }else {
          logger.info("【ConsumerA】消息即将返回队列重新处理 ：{}", msg);
          //设置消息重新回到队列处理
          // requeue表示是否重新回到队列，true重新入队
          channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
//      e.printStackTrace();
    }

  }
}
