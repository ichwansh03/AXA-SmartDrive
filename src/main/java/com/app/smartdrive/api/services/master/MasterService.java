package com.app.smartdrive.api.services.master;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MasterService<D, T, ID> {
    D getById(ID id);
    Page<D> getAll(Pageable pageable);
    D save(T t);
    D update(ID id, T t);
    default void deleteById(ID id) {

    }
}
