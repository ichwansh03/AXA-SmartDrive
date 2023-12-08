package com.app.smartdrive.api.services.customer.impl;

import com.app.smartdrive.api.entities.customer.CustomerInscDoc;
import com.app.smartdrive.api.entities.customer.EnumCustomer;
import com.app.smartdrive.api.repositories.customer.CustomerInscDocRepository;
import com.app.smartdrive.api.services.customer.CustomerInscDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerInscDocServiceImpl implements CustomerInscDocService {

    private  final CustomerInscDocRepository customerInscDocRepository;

    @Override
    public List<CustomerInscDoc> fileCheck(MultipartFile[] files, Long creqEntityId) throws Exception {

        List<CustomerInscDoc> listDoc = new ArrayList<>();

        for (int i = 0; i < files.length; i++) {
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
                    throw  new Exception("Filename contains invalid path sequence " + fileName);
                }

                listDoc.add(CustomerInscDoc.builder()
                        .cadocCreqEntityid(creqEntityId)
                        .cadocFilename(fileName)
                        .cadocFiletype(files[i].getContentType())
                        .cadocFilesize((int) files[i].getSize())
                        .cadocCategory(category)
                        .cadocModifiedDate(LocalDateTime.now())
                        .build());

                // file.transferTo(new File("C:\\Users\\E7450\\michael\\projects\\Java-Northwind-Backend\\src\\main\\resources\\images\\" + file.getOriginalFilename()));

            } catch (Exception e) {
                throw new Exception("Could not save File: " + fileName + " because : " + e.getMessage());
            }

        }

        return listDoc;
    }

    @Override
    public void deleteAllCustomerInscDocInCustomerRequest(Long creqEntityId) {
        this.customerInscDocRepository.deleteAllByCadocCreqEntityid(creqEntityId);
    }


}
