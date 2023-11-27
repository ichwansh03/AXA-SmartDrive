package com.app.smartdrive.api.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class NullUtils {

  public static <T> void updateIfPresent(Consumer<T> consumer, T value) {
    if (value != null) consumer.accept(value);
  }

  public static <T> void updateIfChanged(Consumer<T> consumer, T value, Supplier<T> supplier) {
    if (value != null && !value.equals(supplier.get())) consumer.accept(value);
  }
}
