import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author Tanyu
 * @Date 2020/5/15 14:37
 * @Description
 * @Version 1.0
 */
public class TopicsProducer {

  //交换机名称
  private static final String EXCHANGE_NAME = "publish_subscribe_exchange_topic";
  //交换机类型
  private static final String EXCHANGE_TYPE = "topic";
  //路由键
  private static final String EXCHANGE_ROOT_KEY = "tanyu";

  public static void main(String[] args) throws Exception {
//获取连接
    Connection connection = MqConnecitonUtils.getConnection();
//获取通道
    Channel channel = connection.createChannel();

    //创建交换机对象
    channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

//定义消息
    String message = "这是测试topic类型";
    channel.basicPublish(EXCHANGE_NAME, EXCHANGE_ROOT_KEY, null, message.getBytes());

    System.out.println("发送消息");
  }

}
