package com.app.smartdrive.api.services.service_order.servorder;

import java.util.List;

public interface BaseServiceOrder<E, ID> {

    E getById(ID id);

    List<E> getAll();
}
