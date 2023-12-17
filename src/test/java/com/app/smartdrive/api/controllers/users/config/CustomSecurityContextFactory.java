package com.app.smartdrive.api.controllers.users.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class CustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomUser> {

  @Override
  public SecurityContext createSecurityContext(WithCustomUser withCustomUser) {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(withCustomUser.username(), null, null);
    context.setAuthentication(authenticationToken);
    SecurityContextHolder.setContext(context);
    return context;
  }
}
