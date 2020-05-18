import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;

/**
 * @Author Tanyu
 * @Date 2020/5/15 14:44
 * @Description
 * @Version 1.0
 */
public class TopicsConsumerA {

  private static final String QUEUE_NAME = "topic_queue_name_A";
  private static final String EXCHANGE_NAME = "publish_subscribe_exchange_topic";
  private static final String EXCHANGE_ROOT_KEY = "tanyu";

  public static void main(String[] args) throws Exception {
    //获取连接
    Connection connection = MqConnecitonUtils.getConnection();
    //创建通道
    Channel channel = connection.createChannel();

    //创建对列
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    //将队列绑定到交换机并指定key
    channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, EXCHANGE_ROOT_KEY);
    channel.basicQos(1);

//创建消费者对象
    DefaultConsumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
          byte[] body) throws IOException {
        //消息消费者获取消息
        String message = new String(body, "UTF-8");
        System.out.println("【CustomConsumerA】receive message: " + message);

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

    channel.basicConsume(QUEUE_NAME, false, consumer);

  }
}
