package io.security.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SecurityController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/thread")
    public String thread(HttpSession session){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        System.out.println("authentication = " + authentication.getName());*/

                        Object context = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
                        Authentication authentication = ((SecurityContext) context).getAuthentication();
                        System.out.println("authentication = " + authentication.getName());
                    }
                }
        ).start();
        return "thread";
    }
}
