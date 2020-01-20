package io.security.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpSession;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index(HttpSession session){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Object securityContextObject = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication2 = ((SecurityContext)securityContextObject).getAuthentication();

        return "index";
    }

    @GetMapping("/thread")
    public String thread(HttpSession session){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        Object securityContextObject = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
                        Authentication authentication2 = ((SecurityContext)securityContextObject).getAuthentication();

                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        System.out.println(authentication.getName());


                    }
                }
        ).start();
        return "thread";
    }
}
