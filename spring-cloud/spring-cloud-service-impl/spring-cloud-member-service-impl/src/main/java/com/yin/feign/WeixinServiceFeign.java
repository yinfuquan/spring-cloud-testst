package com.yin.feign;

import com.yin.WeiXinService;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("weixin-service")
public interface WeixinServiceFeign extends WeiXinService {
}
