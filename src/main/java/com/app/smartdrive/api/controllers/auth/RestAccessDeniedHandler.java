package com.app.smartdrive.api.controllers.auth;

import com.app.smartdrive.api.dto.auth.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse httpServletResponse, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    ApiResponse response = new ApiResponse(403, "Access Denied");
    response.setMessage("Access Denied");
    OutputStream out = httpServletResponse.getOutputStream();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(out, response);
    out.flush();
  }
}
