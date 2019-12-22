package io.security.demo;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserDto{

    private String username;
    private String password;
    private List<SimpleGrantedAuthority> roles;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SimpleGrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(List<SimpleGrantedAuthority> roles) {
        this.roles = roles;
    }
}
