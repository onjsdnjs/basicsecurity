package io.security.demo;

import org.springframework.security.core.userdetails.User;

public class UserContext extends User {
  private UserDto user;

  public UserContext(UserDto user) {
    super(user.getUsername(), user.getPassword(), user.getRoles());
    this.user = user;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }
}
