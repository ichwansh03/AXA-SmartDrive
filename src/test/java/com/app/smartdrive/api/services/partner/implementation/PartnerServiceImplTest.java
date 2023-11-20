package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.services.partner.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PartnerServiceImplTest {
    @Autowired
    PartnerService partnerService;

    @Test
    void createPartnerSuccess() {
        Partner partner = new Partner();
        partner = partnerService.save(partner);
        Assertions.assertEquals(partner.getPartEntityid(), partner.getBusinessEntity().getEntityId());
    }
}