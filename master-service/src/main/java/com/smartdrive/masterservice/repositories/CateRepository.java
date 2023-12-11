package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateRepository extends JpaRepository<Category, Long> {

}
