package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.services.service_order.CaevService;
import com.app.smartdrive.api.services.service_order.CaspService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
public class ClaimAssetController {

    private final CaevService caevService;
    private final CaspService caspService;

    @GetMapping("/evidence")
    public ResponseEntity<?> getAllEvidence(){
        return new ResponseEntity<>(caevService.findAllCaev(), HttpStatus.OK);
    }

    @GetMapping("/evidence/search")
    public ResponseEntity<?> getEvidenceById(@RequestParam("caevid") Long caevId){
        return new ResponseEntity<>(caevService.findById(caevId), HttpStatus.OK);
    }

    @GetMapping("/sparepart")
    public ResponseEntity<?> getAllSparepart(){
        return new ResponseEntity<>(caspService.findAllCasp(), HttpStatus.OK);
    }

    @GetMapping("/sparepart/search")
    public ResponseEntity<?> getSparepartById(@RequestParam("caspid") Long caspId){
        return new ResponseEntity<>(caspService.findCaspById(caspId), HttpStatus.OK);
    }
}
