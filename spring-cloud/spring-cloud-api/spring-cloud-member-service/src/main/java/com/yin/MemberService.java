package com.yin;

import org.springframework.web.bind.annotation.GetMapping;

public interface MemberService {

    @GetMapping("/pay")
    String payMoney();

}
