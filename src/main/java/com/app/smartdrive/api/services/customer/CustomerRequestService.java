package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.entities.customer.*;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerRequestService {
    public List<CustomerResponseDTO> get();

    public Page<CustomerResponseDTO> getPaging(Pageable pageable);

    public CustomerResponseDTO getCustomerRequestById(Long creqEntityId);

    public CustomerResponseDTO create(@Valid CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception;

    public CustomerResponseDTO convert(CustomerRequest customerRequest);

    public Double getPremiPrice(String insuraceType, String carBrand, Long zonesId, Double currentPrice, List<CustomerInscExtend> cuexs);

    public Page<CustomerResponseDTO> getPagingUserCustomerRequests(Long custId, Pageable paging, String type, String status);

    public CustomerResponseDTO updateCustomerRequest(UpdateCustomerRequestDTO updateCustomerRequestDTO, MultipartFile[] files) throws Exception;

    public void delete(Long creqEntityId);

    public CustomerRequest createCustomerRequest(BusinessEntity newEntity, User customer, Long entityId);

    public void changeRequestTypeToPolis(CustomerRequestTypeDTO customerRequestTypeDTO);

    public void changeRequestTypeToClaim(CustomerRequestTypeDTO customerRequestTypeDTO);

    public void changeRequestTypeToClose(CustomerRequestTypeDTO customerRequestTypeDTO);

}
