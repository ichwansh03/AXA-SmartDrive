package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CustomerInscDocService {
    List<CustomerInscDoc> fileCheck(MultipartFile[] files, Long creqEntityId) throws Exception;

    void fileUpload(MultipartFile files, Long creqEntityId, File file, String folderPath) throws Exception;

    void deleteAllCustomerInscDocInCustomerRequest(Long creqEntityId);
}
