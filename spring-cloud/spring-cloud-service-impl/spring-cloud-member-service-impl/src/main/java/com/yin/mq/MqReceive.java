package com.yin.mq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.yin.order.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ：yfq
 * @date ：Created in 2019/11/6 15:12
 * @description：
 * @modified By：
 */
@Component
public class MqReceive {
    //    @RabbitListener(
//            bindings = @QueueBinding(                    //数据是否持久化
//                    value = @Queue(value = "directQueue", durable = "true"),
//                    exchange = @Exchange(name = "directExchangeTest",
//                            durable = "true"),
//                    key = "directRoutKey"
//            )
//    )
    @RabbitListener(queues = "directQueue")
    // @RabbitListener放在类上使用 @RabbitHandler 放在方法上
    public void onOrderMessage(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        System.out.println("----收到消息，开始消费-----");
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        long retryCount = getRetryCount(headers);
        System.out.println(retryCount);
        /**
         *  取值为 false 时，表示通知 RabbitMQ 当前消息被确认
         *  如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
         */
        // channel.basicAck(deliveryTag, false);
        System.out.println("--------消费完成--------");

        /**
         * 会一直重发，第三个参数代表是否重新放回队列
         *
         * channel.basicNack(deliveryTag, false,true);
         */

        /**
         * 重试需要抛出异常
         * throw  new RuntimeException();
         */

    }


    public long getRetryCount(Map<String, Object> headers) {
        long retryCount = 0L;
        if (headers != null && headers.containsKey("x-death")) {
            List<Map<String, Object>> deaths = (List<Map<String, Object>>) headers.get("x-death");
            if (deaths.size() > 0) {
                Map<String, Object> death = deaths.get(0);
                retryCount = (Long) death.get("count");
            }
        }
        return retryCount;

    }
}