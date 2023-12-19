package com.app.smartdrive.api.config;

import com.app.smartdrive.api.Exceptions.Error;
import com.app.smartdrive.api.services.jwt.JwtService;
import com.app.smartdrive.api.services.users.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final UserService userService;
  private final ObjectMapper objectMapper;
  private final JwtService jwtService;


  @Value("${jwt.cookie.name}")
  private String jwtCookie;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {

    final Optional<String> jwt = jwtService.getJwtFromCookies(request, jwtCookie);
    if(jwt.isEmpty()){
      String path = request.getRequestURI();
      if(path.startsWith("/api/auth/signin") || path.startsWith("/api/auth/signup")
              || path.startsWith("/api/auth/createAdmin")){
        filterChain.doFilter(request, response);
        return;
      }
//      Error errorResponse = Error.builder()
//              .errorCode("Unauthorized")
//              .message("Unauthorized")
//              .reqMethod(request.getMethod())
//              .url(request.getRequestURI())
//              .status(HttpStatus.UNAUTHORIZED.value())
//              .build();
//      PrintWriter out = response.getWriter();
//      response.setContentType("application/json");
//      response.setStatus(401);
//      response.setCharacterEncoding("UTF-8");
//      out.print(objectMapper.writeValueAsString(errorResponse));
//      out.flush();
      filterChain.doFilter(request,response);
      return;
    }

    Optional<DecodedJWT> decodedJWT = jwtService.getValidatedToken(jwt.get());
    if(decodedJWT.isEmpty()){
      String path = request.getRequestURI();
      if(path.startsWith("/api/auth/refresh") || path.startsWith("/api/auth/signout")){
        filterChain.doFilter(request, response);
        return;
      }
      Error errorResponse = Error.builder()
              .errorCode("Jwt Expired")
              .message("JWT Expired! please refresh the token!")
              .reqMethod(request.getMethod())
              .url(request.getRequestURI())
              .status(HttpStatus.FORBIDDEN.value())
              .build();
      PrintWriter out = response.getWriter();
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.setStatus(HttpStatus.FORBIDDEN.value());
      out.print(objectMapper.writeValueAsString(errorResponse));
      out.flush();
      return;
    }

    if(SecurityContextHolder.getContext().getAuthentication() == null){
      String userName = jwtService.extractUserName(jwt.get());
      UserDetails user = userService.userDetailsService().loadUserByUsername(userName);
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              user, null, user.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      context.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(context);
    }

    filterChain.doFilter(request,response);
  }
}
