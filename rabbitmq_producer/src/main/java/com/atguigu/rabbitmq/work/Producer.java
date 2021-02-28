package com.atguigu.rabbitmq.work;

import com.atguigu.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @Author: yws
 * @Date: 2021/2/22 21:27
 * @Version 1.0
 */
public class Producer {
    static final String QUEUE_NAME = "work_queue";  //队列的名字
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        //为了生产快点，看出效果我们用循环生产消息
        for (int i = 1; i <= 10; i++) {
            String body = i+"hello rabbitmq~~~";
            channel.basicPublish("",QUEUE_NAME,null,body.getBytes());
        }
        channel.close();
        connection.close();
        System.out.println("hotfix 1111");
    }

}
