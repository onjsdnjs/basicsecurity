package io.security.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/invalid")
    public String invalid(){
        return "세션이 중복되었습니다.";
    }

    @GetMapping("/expired")
    public String expired(){
        return "세션이 만료되었습니다..";
    }
}
