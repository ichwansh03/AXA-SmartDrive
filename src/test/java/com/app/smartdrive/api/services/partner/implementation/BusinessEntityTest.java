package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class BusinessEntityTest {
  @Autowired
  private BusinessEntityRepository businessEntityRepository;

  @Test
  void testId() {
    BusinessEntity business = new BusinessEntity();
    business.setEntityModifiedDate(LocalDateTime.now());
    business = businessEntityRepository.save(business);
    Assertions.assertNotNull(business.getEntityId());
    log.info(business.getEntityId().toString());
  }
}
