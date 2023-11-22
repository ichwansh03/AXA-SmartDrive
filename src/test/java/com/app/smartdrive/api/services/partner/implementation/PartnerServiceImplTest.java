package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.repositories.master.CityRepository;
import com.app.smartdrive.api.services.partner.PartnerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest
class PartnerServiceImplTest {
    // @Autowired
    // PartnerService partnerService;

    // @Test
    // void createPartnerSuccess() {
    //     Partner partner = new Partner();
    //     partner = partnerService.save(partner);
    //     Assertions.assertEquals(partner.getPartEntityid(), partner.getBusinessEntity().getEntityId());
    // }
}