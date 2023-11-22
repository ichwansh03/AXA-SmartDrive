package com.app.smartdrive.api.services.service_order.implementation;

import com.app.smartdrive.api.entities.service_order.ClaimAssetEvidence;
import com.app.smartdrive.api.repositories.service_orders.CaevRepository;
import com.app.smartdrive.api.services.service_order.CaevService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaevServiceImpl implements CaevService {

    private final CaevRepository caevRepository;

    @Override
    public ClaimAssetEvidence addFileClaim(MultipartFile file, ClaimAssetEvidence claimAssetEvidence) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")){
                throw new Exception("filename contains invalid path sequence "+fileName);
            }
            if (file.getBytes().length > (Math.pow(1024,2))){
                throw new Exception("file size exceeds maximum limit");
            }

        } catch (MaxUploadSizeExceededException e){
            throw new MaxUploadSizeExceededException(file.getSize());
        } catch (Exception e){
            throw new Exception("could not save file "+fileName);
        }
        return null;
    }

    @Override
    public ClaimAssetEvidence findById(Long caevId) {
        return caevRepository.findById(caevId).get();
    }

    @Override
    public List<ClaimAssetEvidence> findAllCaev() {
        return caevRepository.findAll();
    }
}
