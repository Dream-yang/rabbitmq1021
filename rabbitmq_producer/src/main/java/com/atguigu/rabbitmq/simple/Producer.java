package com.atguigu.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author: yws
 * @Date: 2021/2/22 19:30
 * @Version 1.0
 */
// 生产者，往消息中间件的队列里发送消息
public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.6.100");
        connectionFactory.setPort(5672); //连接端口默认为5672
        connectionFactory.setVirtualHost("/"); //虚拟主机名称;默认为 /
        connectionFactory.setUsername("admin"); //连接用户名；默认为guest
        connectionFactory.setPassword("123456"); //连接密码；默认为guest
        //2.创建连接
        Connection connection = connectionFactory.newConnection();
        //3.创建频道
        Channel channel = connection.createChannel();
        //4. 声明（创建）队列
        /**
         * queue      参数1：队列名称
         * durable    参数2：是否定义持久化队列,当mq重启之后,还在
         * exclusive  参数3：是否独占本次连接
         *            ① 是否独占,只能有一个消费者监听这个队列
         *            ② 当connection关闭时,是否删除队列
         * autoDelete 参数4：是否在不使用的时候自动删除队列,当没有consumer时,自动删除
         * arguments  参数5：队列其它参数
         */
        channel.queueDeclare("simple_queue", true, false, false, null);
        //5.发送消息
        String message = "你好；小兔子咿呀咿呀哟！"; //准备要发送的消息
        /**
         * 参数1：交换机名称,如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：配置信息
         * 参数4：消息内容
         */
        channel.basicPublish("", "simple_queue", null, message.getBytes());
        System.out.println("已发送消息：" + message);
        //6.关闭资源
        channel.close();
        connection.close();
    }
}
