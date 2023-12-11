package com.app.smartdrive.api.controllers.service_order.claim;

import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequest;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequestDto;
import com.app.smartdrive.api.dto.service_order.request.ClaimAssetRequestSparePartDto;
import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.entities.service_order.ClaimAssetSparepart;
import com.app.smartdrive.api.services.service_order.claims.ClaimAssetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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
