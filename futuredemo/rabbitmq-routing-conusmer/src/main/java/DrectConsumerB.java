import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 * @Author Tanyu
 * @Date 2020/5/15 14:11
 * @Description
 * @Version 1.0
 */
public class DrectConsumerB {

  private static final String EXCHANGE_NAME = "publish_subscribe_exchange_direct";
  private static final String QUEUE_NAME = "routing_direct_queue_name_B";
  //binding key
  private static final String EXCHANGE_ROUTE_KEY1 = "error";
  private static final String EXCHANGE_ROUTE_KEY2 = "info";
  private static final String EXCHANGE_ROUTE_KEY3 = "message";

  public static void main(String[] args) throws Exception {

    Connection connection = MqConnecitonUtils.getConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    //将队列绑定到交换机上,并且指定routing_key
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, EXCHANGE_ROUTE_KEY1);

    channel.basicQos(1);

    //创建消费者
    DefaultConsumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {

        //消息消费者获取消息
        String message = new String(body, "UTF-8");
        System.out.println("【CustomConsumerB】receive message: " + message);

        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          //消费完一条消息需要自动发送确认消息给MQ
          channel.basicAck(envelope.getDeliveryTag(), false);
        }
      }
    };

    //使用公平分发必须关闭自动应答
//    Boolean autoAck = false;
    channel.basicConsume(QUEUE_NAME, false, consumer);
  }

}
