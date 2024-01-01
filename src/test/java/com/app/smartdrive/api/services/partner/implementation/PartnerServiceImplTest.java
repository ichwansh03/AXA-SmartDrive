package com.app.smartdrive.api.services.partner.implementation;

import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.repositories.partner.PartnerRepository;
import com.app.smartdrive.api.services.partner.PartnerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PartnerServiceImplTest {

    @MockBean
    PartnerRepository partnerRepository;

    @Autowired
    PartnerService partnerService;


    @Test
    void getByIdThenSuccess() {
        Partner partner = new Partner();
        partner.setPartName("TEST");

        Mockito.when(partnerRepository.findById(1L)).thenReturn(Optional.of(partner));
        Partner result = partnerService.getById(1L);
        Assertions.assertEquals(result, partner);
        Mockito.verify(partnerRepository, Mockito.times(1)).findById(1L);

    }

    @Test
    void getAll() {
    }

    @Test
    void save() {
    }

    @Test
    void testSave() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void create() {
    }

    @Test
    void searchByNameOrNpwp() {
    }
}