package com.app.smartdrive.api.config;

import com.app.smartdrive.api.dto.auth.response.ApiResponse;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private  JwtService jwtServiceImpl;
  private final UserService userService;
  @Value("${jwt.refresh.cookie}")
  private String jwtRefreshCookie;

  @Value("${jwt.cookie.name}")
  private String jwtCookie;
//  private final UserDetailsService userDetailsService;
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {

    final String jwt = jwtServiceImpl.getJwtFromCookies(request, jwtCookie);
    if(!StringUtils.hasLength(jwt)){
      filterChain.doFilter(request, response);
      return;
    }

    if(request.getRequestURI().startsWith("/api/auth/refresh")){
      filterChain.doFilter(request,response);
      return;
    }
    try {
      if(jwtServiceImpl.validateJwtToken(jwt)
      && SecurityContextHolder.getContext().getAuthentication() == null){
        String userName = jwtServiceImpl.extractUserName(jwt);
        UserDetails user = userService.userDetailsService().loadUserByUsername(userName);
        if(jwtServiceImpl.isTokenValid(jwt, user)){
          SecurityContext context = SecurityContextHolder.createEmptyContext();
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                  user, null, user.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          context.setAuthentication(authenticationToken);
          SecurityContextHolder.setContext(context);
        }
      }
    } catch (ExpiredJwtException e) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      ApiResponse res = new ApiResponse(401, "Unauthorised");
      res.setMessage("Unauthorised");
      OutputStream out = response.getOutputStream();
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(out, res);
      out.flush();
      filterChain.doFilter(request,response);
      return;
    }
    filterChain.doFilter(request,response);
  }
}
