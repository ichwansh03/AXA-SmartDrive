package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateRepository extends JpaRepository<Category, Long> {

}
