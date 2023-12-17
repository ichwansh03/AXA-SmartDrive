package com.smartdrive.serviceorderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaevDto {
    private Long caevId;
    private String caevFileName;
    private Long caevFileSize;
    private String caevFileType;
    private String caevUrl;
    private String caevNote;
}
