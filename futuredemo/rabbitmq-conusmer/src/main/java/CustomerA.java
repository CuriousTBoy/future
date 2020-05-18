import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Author Tanyu
 * @Date 2020/5/15 11:18
 * @Description 消费者
 * @Version 1.0
 */
public class CustomerA {

  //队列名称
  private static final String QUEUE = "helloworld_queue";

  public static void main(String[] args)throws Exception {
    System.out.println("[消费者A启动....]");
    // 创建与RabbitMQ服务的TCP连接
    Connection connection = MqConnecitonUtils.getConnection();
    // /创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
    final Channel channel = connection.createChannel();

    //开启公平分发
    channel.basicQos(1);


    //声明队列
    channel.queueDeclare(QUEUE, true, false, false, null);

    DefaultConsumer consumer=new DefaultConsumer(channel){
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        String exchange = envelope.getExchange();
        String routingKey = envelope.getRoutingKey();
        long deliveryTag = envelope.getDeliveryTag();
        try {
          System.out.println("【消费者A】");
          //消息内容
          String msg = new String(body, "UTF-8");
          System.out.println("接收消息的交换机是:"+exchange);
          System.out.println("接收消息的routingKey是:"+routingKey);
          System.out.println("消息的主键是:"+deliveryTag);
          System.out.println("消息内容是:" + msg);
        } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
        } finally {
          //手动设置消息被消费了
          channel.basicAck(deliveryTag,false);
        }
      }
    };


    //使用公平分发必须关闭自动应答(autoAck：true自动返回结果，false手动返回)
    boolean autoAck = false;

    /*** 监听队列
     * String queue, boolean autoAck,Consumer callback
     * 参数明细
     * 1、队列名称
     * 2、是否自动回复，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置 为false则需要手动回复
     * 3、消费消息的方法，消费者接收到消息后调用此方法
     */

    channel.basicConsume(QUEUE,autoAck,consumer);
  }


}
