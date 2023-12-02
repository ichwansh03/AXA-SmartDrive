package com.app.smartdrive.api.services.customer;

import com.app.smartdrive.api.dto.customer.request.CiasDTO;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestDTO;
import com.app.smartdrive.api.dto.customer.response.*;
import com.app.smartdrive.api.dto.user.BussinessEntityResponseDTO;
import com.app.smartdrive.api.entities.customer.*;
import com.app.smartdrive.api.entities.master.CarSeries;
import com.app.smartdrive.api.entities.master.Cities;
import com.app.smartdrive.api.entities.master.InsuranceType;
import com.app.smartdrive.api.entities.master.TemplateInsurancePremi;
import com.app.smartdrive.api.entities.users.BusinessEntity;
import com.app.smartdrive.api.entities.users.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public interface CustomerRequestService {
    public List<CustomerRequest> get();

    public Page<CustomerResponseDTO> getPaging(Pageable pageable);

    public CustomerResponseDTO create(@Valid CustomerRequestDTO customerRequestDTO, MultipartFile[] files) throws Exception;

    public List<CustomerInscDoc> fileCheck(MultipartFile[] files, Long creqEntityId) throws Exception;

    public CustomerResponseDTO convert(CustomerRequest customerRequest);

    public Double getPremiPrice(String insuraceType, String carBrand, Long zonesId, Double currentPrice, List<CustomerInscExtend> cuexs);

    Page<CustomerResponseDTO> getPagingCustomer(Long custId, Pageable paging, String type, String status);
}
