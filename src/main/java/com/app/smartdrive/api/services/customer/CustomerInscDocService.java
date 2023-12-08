package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerInscDocService {
    List<CustomerInscDoc> fileCheck(MultipartFile[] files, Long creqEntityId) throws Exception;

    void deleteAllCustomerInscDocInCustomerRequest(Long creqEntityId);
}
