package com.smartdrive.masterservice.repositories;

import com.smartdrive.masterservice.entities.InboxMessaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IbmeRepository extends JpaRepository<InboxMessaging, Long> {
}
