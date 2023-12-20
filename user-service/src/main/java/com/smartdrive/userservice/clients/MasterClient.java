package com.smartdrive.userservice.clients;

import com.smartdrive.userservice.dto.response.CitiesRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "master-service",url = "${application.config.master-url}")
public interface MasterClient {
    @GetMapping("/cities/{id}")
    CitiesRes findDataById(@PathVariable Long id);
}
