package com.app.smartdrive.api.services;

import java.util.List;


public interface BaseService<T, ID> {
    T getById(ID id);
    List<T> getAll();
    T save(T t);
    default void deleteById(ID id) {

    }
}
