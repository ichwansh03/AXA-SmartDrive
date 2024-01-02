package com.app.smartdrive.api.services.master;

import java.util.List;


public interface MasterService<D, T, ID> {
    D getById(ID id);
    List<D> getAll();
    D save(T t);
    default void deleteById(ID id) {

    }
}
