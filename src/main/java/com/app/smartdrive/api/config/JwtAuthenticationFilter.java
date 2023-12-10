package com.app.smartdrive.api.config;

import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.users.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserService userService;
//  private final UserDetailsService userDetailsService;
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
//    final String authHeader = request.getHeader("Authorization");
    final String jwt = jwtService.getJwtFromCookies(request);
    final String userName;
    if(!StringUtils.hasLength(jwt)){
      filterChain.doFilter(request, response);
      return;
    }
    userName = jwtService.extractUserName(jwt);
    if(StringUtils.hasLength(userName)
      && SecurityContextHolder.getContext().getAuthentication() == null){
      UserDetails userDetail = userService.userDetailsService()
              .loadUserByUsername(userName);
      if(jwtService.isTokenValid(jwt, userDetail)){
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetail, null, userDetail.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);
      }
    }
    filterChain.doFilter(request,response);
  }
}
