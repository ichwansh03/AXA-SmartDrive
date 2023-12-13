package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.repositories.customer.CustomerInscDocRepository;
import com.app.smartdrive.api.services.customer.CustomerInscDocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerInscDocServiceImpl implements CustomerInscDocService {

    private  final CustomerInscDocRepository customerInscDocRepository;

    @Override
    public List<CustomerInscDoc> fileCheck(MultipartFile[] files, Long creqEntityId) throws Exception {

        List<CustomerInscDoc> listDoc = new ArrayList<>();

        String basePath = new File("").getAbsolutePath();
        String path = basePath + "\\src\\main\\resources\\images\\cadoc\\";
        String folderPath = path + creqEntityId.toString() + "\\";

        File file = new File(folderPath);

        if (file.exists()) {
            FileUtils.cleanDirectory(file);
        }

        for (int i = 0; i < files.length; i++) {
            if(files[i].isEmpty()){
                continue;
            }

            String fileName = StringUtils.cleanPath(files[i].getOriginalFilename());
            EnumCustomer.CadocCategory category;

            if(i == 0){
                category = EnumCustomer.CadocCategory.KTP;
            }else if(i == 1){
                category = EnumCustomer.CadocCategory.SIUP;
            }else{
                category = EnumCustomer.CadocCategory.TDP;
            }

            try {

                if(fileName.contains("..")) {
                    throw new Exception("Filename contains invalid path sequence " + fileName);
                }

                if(fileName.length() > 15){
                    throw new Exception("file names cannot exceed 15 characters " + fileName);
                }


                listDoc.add(CustomerInscDoc.builder()
                        .cadocCreqEntityid(creqEntityId)
                        .cadocFilename(fileName)
                        .cadocFiletype(files[i].getContentType())
                        .cadocFilesize((int) files[i].getSize())
                        .cadocCategory(category)
                        .cadocModifiedDate(LocalDateTime.now())
                        .build());

                fileUpload(files[i], creqEntityId, file, folderPath);
                // file.transferTo(new File("C:\\Users\\E7450\\michael\\projects\\Java-Northwind-Backend\\src\\main\\resources\\images\\" + file.getOriginalFilename()));

            } catch (Exception e) {
                throw new Exception("Could not save File: " + fileName + " because : " + e.getMessage());
            }


        }

        log.info("CustomerInscDocServiceImpl::fileCheck, successfully document checking");
        return listDoc;
    }


    @Override
    public void fileUpload(MultipartFile filePhoto, Long creqEntityId, File file, String folderPath) throws Exception {

        if (!file.exists()) {
            if (file.mkdir()) {
                // file.transferTo(new File("C:\\Users\\E7450\\michael\\projects\\Java-Northwind-Backend\\src\\main\\resources\\images\\" + file.getOriginalFilename()));
                filePhoto.transferTo(new File(folderPath + filePhoto.getOriginalFilename()));
                log.info("Directory is created!");
            } else {
                log.info("Failed to create directory!");
            }
        }else{
            filePhoto.transferTo(new File(folderPath + filePhoto.getOriginalFilename()));
        }


    }



    @Transactional
    @Override
    public void deleteAllCustomerInscDocInCustomerRequest(Long creqEntityId) {
        this.customerInscDocRepository.deleteAllByCadocCreqEntityid(creqEntityId);
        log.info("CustomerInscDocServiceImpl::deleteAllCustomerInscDocInCustomerRequest, delete all cadoc for inserting a new one");
    }


}
