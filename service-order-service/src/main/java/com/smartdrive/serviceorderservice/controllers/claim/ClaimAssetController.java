package com.smartdrive.serviceorderservice.controllers.claim;

import com.smartdrive.serviceorderservice.dto.request.ClaimAssetRequestDto;
import com.smartdrive.serviceorderservice.dto.request.ClaimAssetRequestSparePartDto;
import com.smartdrive.serviceorderservice.entities.ClaimAssetEvidence;
import com.smartdrive.serviceorderservice.entities.ClaimAssetSparepart;
import com.smartdrive.serviceorderservice.services.claims.ClaimAssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/claim-asset")
@RequiredArgsConstructor
public class ClaimAssetController {

    private final ClaimAssetService claimAssetService;

    @PostMapping(path = "/evidence", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ClaimAssetEvidence>> create(@ModelAttribute @Valid ClaimAssetRequestDto claimAssetRequestDto) throws IOException {
        return ResponseEntity.status(201).body(claimAssetService.create(claimAssetRequestDto));
    }

    @PostMapping(path = "/spare-part", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<ClaimAssetSparepart>> create(@ModelAttribute @Valid ClaimAssetRequestSparePartDto caspRequestDto) throws IOException {
        return ResponseEntity.status(201).body(claimAssetService.create(caspRequestDto));
    }




}
