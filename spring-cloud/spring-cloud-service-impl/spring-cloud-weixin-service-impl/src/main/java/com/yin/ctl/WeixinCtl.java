package com.yin.ctl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.yin.WeiXinService;
import com.yin.mq.MqSend;
import com.yin.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yfq
 * @date ：Created in 2019/11/4 14:29
 * @description：
 * @modified By：
 */
@RestController
@RequestMapping("/weixin")
public class WeixinCtl implements WeiXinService {

    @Autowired
    private MqSend mqSend;

    @Override
    @SentinelResource
    public String getWeixinInfo() {
        return "weixin la ji";
    }

    @GetMapping("/pay/{money}")
    public String weixinPay(@PathVariable(value = "money") Long money) {
        Order order = new Order();
        order.setId(1).setMessage_id("meg-id").setName(String.valueOf(money));
        mqSend.send(order);
        return "success";

    }

}
