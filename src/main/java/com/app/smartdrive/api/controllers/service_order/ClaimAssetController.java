package com.app.smartdrive.api.controllers.service_order;

import com.app.smartdrive.api.dto.service_order.CaevDto;
import com.app.smartdrive.api.dto.service_order.CaspDto;
import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.services.service_order.CaevService;
import com.app.smartdrive.api.services.service_order.CaspService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/claim")
@RequiredArgsConstructor
public class ClaimAssetController {

    private final CaevService caevService;
    private final CaspService caspService;

    @GetMapping("/evidence/search")
    public ResponseEntity<?> getEvidenceById(@RequestParam("seroId") String seroId){

        List<ClaimAssetEvidence> allByCaevSeroId = caevService.findAllByCaevSeroId(seroId);

        List<CaevDto> caevDtoList = allByCaevSeroId.stream()
                .map(evidence -> CaevDto.builder()
                        .caevId(evidence.getCaevId())
                        .caevFileSize(evidence.getCaevFileSize())
                        .caevNote(evidence.getCaevNote())
                        .caevFileName(evidence.getCaevFileName())
                        .caevFileType(evidence.getCaevFileType())
                        .caevUrl(evidence.getCaevUrl())
                        .build()).toList();

        return new ResponseEntity<>(caevDtoList, HttpStatus.OK);
    }

    @GetMapping("/sparepart/search")
    public ResponseEntity<?> getSparepartById(@RequestParam("seroId") String seroId){
        List<ClaimAssetSparepart> allByCaspSeroId = caspService.findAllByCaspSeroId(seroId);

        List<CaspDto> caspDtoList = allByCaspSeroId.stream()
                .map(sparepart -> CaspDto.builder()
                        .caspId(sparepart.getCaspId())
                        .caspItemName(sparepart.getCaspItemName())
                        .caspQuantity(sparepart.getCaspQuantity())
                        .caspSubtotal(sparepart.getCaspSubtotal())
                        .caspItemPrice(sparepart.getCaspItemPrice())
                        .build()).toList();

        return new ResponseEntity<>(caspDtoList, HttpStatus.OK);
    }
}
