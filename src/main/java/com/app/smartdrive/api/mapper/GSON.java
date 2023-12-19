package com.app.smartdrive.api.mapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GSON {
  private static final Gson gson;
  static {
    gson = new GsonBuilder().serializeNulls().create();
  }
  public static String toJson(Object object){
    return gson.toJson(object);
  }
}