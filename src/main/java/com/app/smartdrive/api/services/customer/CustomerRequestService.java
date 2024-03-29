package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.dto.customer.request.*;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.user.request.CreateUserDto;
import com.app.smartdrive.api.entities.customer.*;
import com.app.smartdrive.api.entities.service_order.Services;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomerRequestService {
    public List<CustomerResponseDTO> get();

    Page<CustomerResponseDTO> getAllPaging(Pageable paging, String type, String status);

    public Page<CustomerResponseDTO> getPagingUserCustomerRequest(Long customerId, Pageable paging, String type, String status);

    public Page<CustomerResponseDTO> getPagingAgenCustomerRequest(Long employeeId, String arwgCode, Pageable pageable, String type, String status);

    public CustomerRequest getById(Long creqEntityId);

    public CustomerResponseDTO create(CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception;

    public CustomerResponseDTO createByAgen(CreateCustomerRequestByAgenDTO customerRequestDTO, MultipartFile[] files) throws Exception;

    public CustomerRequest createCustomerRequest(BusinessEntity newEntity, User customer, Long entityId);

    public CustomerRequest createCustomerRequestByAgen(BusinessEntity newEntity, User customer, Long entityId);

    public User createNewUserByAgen(CreateUserDto userPost, LocalDateTime birthDate, Boolean isActive);

    public CustomerResponseDTO updateCustomerRequest(UpdateCustomerRequestDTO updateCustomerRequestDTO, MultipartFile[] files) throws Exception;

    public void updatedCustomerRoleStatus(Long userEntityId, Boolean grantUserAccess);

    public void validateUser(CreateUserDto createUserDto);

    public void changeRequestType(CustomerRequest customerRequest, EnumCustomer.CreqType creqType);

    public void changeRequestStatus(CustomerRequest customerRequest, EnumCustomer.CreqStatus creqStatus);

    public void delete(Long creqEntityId);

    void mapCustomerRequestToDtoServices(Services services, ServiceRespDto serviceRespDto);
}
