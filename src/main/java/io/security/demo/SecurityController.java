package io.security.demo;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class SecurityController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public void user(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication: " + authentication);
        System.out.println("user: " + authentication.getName());

        Object securityContextObject = session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        Authentication authentication2 = ((SecurityContext)securityContextObject).getAuthentication();
        System.out.println("authentication: " + authentication2);
        System.out.println("user: " + authentication.getName());

    }
}
