package com.app.smartdrive.api.repositories.service_orders;

import com.app.smartdrive.api.entities.service_order.ServicePremiCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecrRepository extends JpaRepository<ServicePremiCredit, Long> {

    //List<ServicePremiCredit> findAllBySecrServId(Long servId);
}
