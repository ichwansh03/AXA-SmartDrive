package com.app.smartdrive.api.controllers.users.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class TestConfigUser {
  @Bean
  @Primary
  public UserDetailsService userDetailsService() {
    UserDetails user1 = new User("users", "sss", Arrays.asList(new SimpleGrantedAuthority("Employee")));
    return new InMemoryUserDetailsManager(user1);
  }
}