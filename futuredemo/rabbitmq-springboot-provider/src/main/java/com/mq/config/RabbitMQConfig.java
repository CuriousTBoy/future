package com.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author Tanyu
 * @Date 2020/5/15 16:14
 * @Description RabbitMQ配置信息，绑定交换器、队列、路由键设置
 *  说明：
 *  1. 声明Exchange交换器；
 *  2. 声明Queue队列；
 *  3. 绑定：BindingBuilder绑定队列到交换器，并设置路由键；
 * @Version 1.0
 */
@Component
public class RabbitMQConfig {

  private static final String EXCHANGE_NAME = "message_confirm_exchange";

  private static final String QUEUE_NAME = "message_confirm_queue";

  private static final String ROUTING_KEY = "user.#";


  @Bean
  private TopicExchange topicExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  private Queue queue() {
    return new Queue(QUEUE_NAME);
  }

  @Bean
  private Binding bindingDirect() {
    return BindingBuilder.bind(queue()).to(topicExchange()).with(ROUTING_KEY);
  }
}
