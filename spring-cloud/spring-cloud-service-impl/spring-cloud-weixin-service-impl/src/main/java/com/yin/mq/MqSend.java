package com.yin.mq;

import com.yin.order.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ：yfq
 * @date ：Created in 2019/11/6 15:12
 * @description：
 * @modified By：
 */

/*
使用场景：

        如果消息没有到exchange,则confirm回调,ack=false

        如果消息到达exchange,则confirm回调,ack=true

        exchange到queue成功,则不回调return

        exchange到queue失败,则回调return(需设置mandatory=true,否则不回回调,消息就丢了)*/

@Component
public class MqSend {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;


    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {

        /**
         *
         * @param correlationData 唯一标识，有了这个唯一标识，我们就知道可以确认（失败）哪一条消息了
         * @param ack  是否投递成功
         * @param cause 失败原因
         */
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();

            try {

                log.info("messageId:"+messageId);

            } catch (IndexOutOfBoundsException e) {
                log.error("不存在messageId:{}的日志记录",messageId);
            }

            /**
             * 模拟查询数据库返回BrokerMessageLog
             *
             */


            //返回成功，表示消息被正常投递
            if (ack) {

                log.info("信息投递成功，messageId:{}",messageId);

            } else {
                log.error("消费信息失败，messageId:{} 原因:{}",messageId,cause);
            }
        }
    };


    final RabbitTemplate.ReturnCallback returnCallback=  new RabbitTemplate.ReturnCallback(){
        @Override
        public void returnedMessage(Message message, int i, String s, String s1, String s2) {
            log.info("收到回调，成功发送到消费者");
        }
    };

    /**
     * 信息投递的方法
     * @param order
     * @throws Exception
     */
    public void send(Order order)  {

        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessage_id());
        /**
         * 消息延时2秒
         */

        rabbitTemplate.convertAndSend("directExchangeTest", "directRoutKey",
                order, message -> {
                    message.getMessageProperties().setMessageId("123");
                    message.getMessageProperties().setDelay(2000);
                    return message;
                }, correlationData);
    }
}
