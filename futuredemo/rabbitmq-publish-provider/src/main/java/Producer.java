import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author Tanyu
 * @Date 2020/5/15 11:44
 * @Description
 * @Version 1.0
 */
public class Producer {

  //交换机的名字
  private static final String PUBLISH_SUBSCRIBE_EXCHANGE_NAME = "publish_subscribe_exchange_fanout";

  //交换机类型：分发
  private static final String PUBLISH_SUBSCRIBE_EXCHANGE_TYPE = "fanout";

  public static void main(String[] args)throws Exception {
    //获取MQ连接
    Connection connection = MqConnecitonUtils.getConnection();

    //从连接中获取Channel通道对象
    Channel channel = connection.createChannel();

    //创建交换机对象publish_subscribe_exchange_fanout
    channel.exchangeDeclare(PUBLISH_SUBSCRIBE_EXCHANGE_NAME,PUBLISH_SUBSCRIBE_EXCHANGE_TYPE);

    //发送消息到交换机exchange上
    String msg = "hello world!!!";

    channel.basicPublish(PUBLISH_SUBSCRIBE_EXCHANGE_NAME,"",null,msg.getBytes());

    System.out.println("发送消息完毕~~~~~");


  }

}
