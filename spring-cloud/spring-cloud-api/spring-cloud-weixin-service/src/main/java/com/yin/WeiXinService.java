package com.yin;

import org.springframework.web.bind.annotation.GetMapping;

public interface WeiXinService {

    @GetMapping("/weixin")
    String getWeixinInfo();

}
