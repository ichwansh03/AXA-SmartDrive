package com.app.smartdrive.api.services.service_order.premi;

import com.app.smartdrive.api.dto.service_order.request.SecrReqDto;
import com.app.smartdrive.api.dto.service_order.response.SecrDto;
import com.app.smartdrive.api.entities.service_order.ServicePremi;
import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import jakarta.persistence.LockModeType;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;

public interface ServPremiCreditService {

    List<ServicePremiCredit> findByServId(Long servId);

    List<ServicePremiCredit> addSecr(ServicePremi servicePremi);

    boolean updateSecr(SecrReqDto secrReqDto);

    boolean checkPremiPayment(ServicePremiCredit servicePremiCredit);

    ServicePremiCredit findByDueDate();
}
