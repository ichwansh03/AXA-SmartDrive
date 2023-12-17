package com.smartdrive.partnerservice.clients;

import com.smartdrive.partnerservice.dto.ArwgRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "master-service",url = "${application.config.master-url}")
public interface MasterClient {
    @GetMapping("/arwg/{arwgCode}")
    ArwgRes findArwgByCode(@PathVariable("arwgCode") String arwgCode);
}
