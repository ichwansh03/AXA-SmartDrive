package com.app.smartdrive.api.repositories.master;

import com.app.smartdrive.api.entities.master.Provinsi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvRepository extends JpaRepository<Provinsi, Integer> {
}
