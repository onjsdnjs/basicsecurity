package io.security.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/denied")
    public String denied(){
        return "denied";
    }

    @GetMapping("login")
    public String login(){
        return "custom login";
    }
}
