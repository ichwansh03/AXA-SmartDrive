package com.smartdrive.serviceorderservice.services.claims;

import com.smartdrive.serviceorderservice.dto.request.ClaimAssetRequestDto;
import com.smartdrive.serviceorderservice.dto.request.ClaimAssetRequestSparePartDto;
import com.smartdrive.serviceorderservice.entities.ClaimAssetEvidence;
import com.smartdrive.serviceorderservice.entities.ClaimAssetSparepart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClaimAssetService {

    void init();

    ClaimAssetEvidence add(MultipartFile multipartFile, ClaimAssetEvidence caev) throws Exception;
    List<ClaimAssetEvidence> create(ClaimAssetRequestDto claimAssetRequestDto) throws IOException;

    List<ClaimAssetSparepart> create(ClaimAssetRequestSparePartDto claimAssetRequestDto) throws IOException;


}
