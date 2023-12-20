package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.entities.service_order.ServiceOrderWorkorder;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.users.BusinessEntityRepository;
import com.app.smartdrive.api.services.service_order.servorder.impl.ServOrderWorkorderImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
public class BusinessEntityTest {
  @Autowired
  private ServOrderWorkorderImpl servOrderWorkorder;

  @Test
  void testId() {
    List<ServiceOrderWorkorder> workorderList = servOrderWorkorder.findAllByPartnerAndArwgCode(1060L, null);
    Assertions.assertEquals(2, workorderList.size());
  }
}
