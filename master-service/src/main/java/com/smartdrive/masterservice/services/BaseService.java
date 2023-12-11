package com.smartdrive.masterservice.services;

import java.util.List;


public interface BaseService<T, ID> {
    T getById(ID id);
    List<T> getAll();
    T save(T entity);
    default void deleteById(ID id) {

    }
}
