package com.yin.ctl;

import com.yin.WeiXinService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：yfq
 * @date ：Created in 2019/11/4 14:29
 * @description：
 * @modified By：
 */
@RestController
public class WeixinCtl implements WeiXinService {

    @Override
    public String getWeixinInfo() {
        return "weixin la ji";
    }

}