package com.yin.ctl;

import com.yin.feign.WeixinServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yfq
 * @date ：Created in 2019/11/4 14:32
 * @description：
 * @modified By：
 */
@RestController
public class MemberCtl {
    @Autowired
    private WeixinServiceFeign weixinServiceFeign;

    @GetMapping("/member/weixin")
    public String getWeixin() {
        return weixinServiceFeign.getWeixinInfo();
    }


}
