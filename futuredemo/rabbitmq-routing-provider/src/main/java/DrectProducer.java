import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author Tanyu
 * @Date 2020/5/15 14:07
 * @Description
 * @Version 1.0
 */
public class DrectProducer {

  private static final String EXCHANGE_NAME = "publish_subscribe_exchange_direct";
  //交换机类型：direct
  private static final String EXCHANGE_TYPE = "direct";
  // 路由键
  private static final String EXCHANGE_ROUTE_KEY = "info";

  public static void main(String[] args) throws Exception{

    //获取MQ连接
    Connection connection = MqConnecitonUtils.getConnection();
    //从连接中获取Channel通道对象
    Channel channel = null;
    channel = connection.createChannel();

    //创建交换机对象  direct
    channel.exchangeDeclare(EXCHANGE_NAME,EXCHANGE_TYPE);

    //发送消息到交换机exchange上
    String msg = "hello world!!!!";

    //指定routing key为info
    channel.basicPublish(EXCHANGE_NAME,EXCHANGE_ROUTE_KEY,null,msg.getBytes());

    System.out.println("发送消息");

  }

}
