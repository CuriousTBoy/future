import com.mq.utils.MqConnecitonUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author Tanyu
 * @Date 2020/5/15 10:40
 * @Version 1.0
 */
public class Producer {

  //队列名称
  private static final String QUEUE = "helloworld_queue";

  public static void main(String[] args) throws Exception {
    // 创建与RabbitMQ服务的TCP连接
    Connection connection = MqConnecitonUtils.getConnection();
    // /创建与Exchange的通道，每个连接可以创建多个通道，每个通道代表一个会话任务
    Channel channel = connection.createChannel();
    /*** 声明队列，如果Rabbit中没有此队列将自动创建
     * param1:队列名称
     * param2:是否持久化  队列中的消息是否进行持久化  服务器哦重启关机数据是否丢失
     * param3:队列是否独占此连接
     * param4:队列不再使用时是否自动删除此队列
     * param5:队列参数 设置一些额外参数！
     ***/
    channel.queueDeclare(QUEUE, true, false, false, null);

    //开启公平分发
    channel.basicQos(1);

    for (int i = 0; i < 20; i++) {
      String message = "这是测试信息" + i + ":" + System.currentTimeMillis();
      /***
       * 消息发布方法
       * param1：Exchange的名称，如果没有指定，则使用Default Exchange
       * 这里没有指定交换机，消息将发送给默认交换机，每个队列也会绑定那个默认的交换机，但是不能显 示绑定或解除绑定
       * param2:routingKey,消息的路由Key，是用于Exchange（交换机）将消息转发到指定的消息队列
       * param3:消息包含的属性  消息的属性信息
       * param4：消息体
       */
      channel.basicPublish("", QUEUE, null, message.getBytes());
    }

    System.out.println("消息发送完毕");
    channel.close();
    connection.close();
  }
}
