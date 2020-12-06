package com.github.dylanz666.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author : dylanz
 * @since : 12/06/2020
 */
@Component
public class MessageReceiver implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] c = message.getChannel();
        byte[] b = message.getBody();

        try {
            String channel = new String(c, StandardCharsets.UTF_8);
            String body = new String(b, StandardCharsets.UTF_8);

            //在此处做接收到消息后的业务处理
            System.out.println("channel: " + channel);
            System.out.println("body: " + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
