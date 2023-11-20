package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.entities.master.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category saveOne();
}
