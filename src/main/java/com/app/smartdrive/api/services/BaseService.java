package com.app.smartdrive.api.services;

import java.util.List;

import com.app.smartdrive.api.entities.users.User;

public interface BaseService<T, ID> {
    T getById(ID id);
    List<T> getAll();
    T save(T entity);
    void deleteById(ID id);
}
