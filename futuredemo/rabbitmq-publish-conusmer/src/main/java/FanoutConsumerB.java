import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 * @Author Tanyu
 * @Date 2020/5/15 11:54
 * @Description
 * @Version 1.0
 */
public class FanoutConsumerB {
  //交换机的名字
  private static final String PUBLISH_SUBSCRIBE_EXCHANGE_NAME = "publish_subscribe_exchange_fanout";


  private static final String PUBLIC_SUBSCRIBE_QUEUE_NAME = "public_subscribe_queue_name_B";

  public static void main(String[] args) throws Exception {

    //获取MQ连接对象
    Connection connection = MqConnecitonUtils.getConnection();
    //创建消息通道对象
    final Channel channel = connection.createChannel();
    //创建队列
    channel.queueDeclare(PUBLIC_SUBSCRIBE_QUEUE_NAME, false, false, false, null);
    //绑定交换机
    //将队列绑定到交换机上
    channel.queueBind(PUBLIC_SUBSCRIBE_QUEUE_NAME, PUBLISH_SUBSCRIBE_EXCHANGE_NAME, "");

    //创建消费者对象
    DefaultConsumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        //消息消费者获取消息
        String message = new String(body, "UTF-8");
        System.out.println("【CustomConsumerB】receive message: " + message);
      }
    };
    //监听消息队列
    channel.basicConsume(PUBLIC_SUBSCRIBE_QUEUE_NAME, true, consumer);
  }

}
