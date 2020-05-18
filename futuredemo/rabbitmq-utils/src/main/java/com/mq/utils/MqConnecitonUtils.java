package com.mq.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author Tanyu
 * @Date 2020/5/15 10:19
 * @Version 1.0
 */
public class MqConnecitonUtils {

  private static final String RABBITMQ_HOST = "127.0.0.1";
  private static final Integer RABBITMQ_PORT = 5672;
  private static final String RABBITMQ_VHOST = "/";
  private static final String RABBITMQ_USERNAME = "guest";
  private static final String RABBITMQ_PASSWORD = "guest";


  public static Connection getConnection(){

    //定义MQ连接对象
    Connection connection = null;
    //创建MQ连接工厂对象
    ConnectionFactory connectionFactory = new ConnectionFactory();
    // 设置MQ主机名称
    connectionFactory.setHost(RABBITMQ_HOST);
    // 设置MQ AMQP端口号
    connectionFactory.setPort(RABBITMQ_PORT);
    // 设置MQ 连接的virtual host
    connectionFactory.setVirtualHost(RABBITMQ_VHOST);
    // 设置MQ 用户名称
    connectionFactory.setUsername(RABBITMQ_USERNAME);
    // 设置MQ 用户密码
    connectionFactory.setPassword(RABBITMQ_PASSWORD);

    try {
      connection = connectionFactory.newConnection();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
    return connection;
  }


}
