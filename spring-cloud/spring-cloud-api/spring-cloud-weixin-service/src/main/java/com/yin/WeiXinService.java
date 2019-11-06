package com.yin;

import org.springframework.web.bind.annotation.GetMapping;

public interface WeiXinService {

    @GetMapping("/test")
    String getWeixinInfo();

}
