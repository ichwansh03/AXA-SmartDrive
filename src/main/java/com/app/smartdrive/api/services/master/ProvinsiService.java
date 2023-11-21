package com.app.smartdrive.api.services.master;

import com.app.smartdrive.api.dto.master.ProvinsiDto;
import com.app.smartdrive.api.entities.master.Provinsi;
import com.app.smartdrive.api.services.BaseService;

import java.util.List;

public interface ProvinsiService extends BaseService<Provinsi, Long> {
    List<ProvinsiDto> findAll();
}
