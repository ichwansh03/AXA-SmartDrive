package com.app.smartdrive.api.controllers.master;

import com.app.smartdrive.api.controllers.BaseController;
import com.app.smartdrive.api.dto.HR.EmployeeAreaWorkgroupDto;
import com.app.smartdrive.api.dto.master.AreaWorkGroupDto;
import com.app.smartdrive.api.dto.service_order.response.ServiceRespDto;
import com.app.smartdrive.api.dto.service_order.response.SoTasksDto;
import com.app.smartdrive.api.entities.master.AreaWorkGroup;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.services.master.ArwgService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/master/arwg")
@Tag(name = "Master Module", description = "This Tab Contains All Operation for Master Module")
public class ArwgController implements BaseController<AreaWorkGroupDto, String> {
    private final ArwgService service;

    @Override
    @GetMapping
    public ResponseEntity<?> findAllData() {
        List<AreaWorkGroup> areaWorkGroup = service.getAll();
        List<AreaWorkGroupDto> result = areaWorkGroup.stream().map(arwg -> {
            return new AreaWorkGroupDto(arwg.getArwgCode(), arwg.getArwgDesc(), arwg.getArwgCityId(), TransactionMapper.mapEntityListToDtoList(arwg.getEmployeeAreaWorkgroup(), EmployeeAreaWorkgroupDto.class),
                    TransactionMapper.mapEntityListToDtoList(arwg.getServiceOrders(), ServiceRespDto.class), TransactionMapper.mapEntityListToDtoList(arwg.getServiceOrderTasks(), SoTasksDto.class));
        }).toList();
        return ResponseEntity.ok(result);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findDataById(@PathVariable String id) {
        AreaWorkGroup areaWorkGroup = service.getById(id);
        return ResponseEntity.ok(TransactionMapper.mapEntityToDto(areaWorkGroup, AreaWorkGroupDto.class));
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<?> saveData(@Valid @RequestBody AreaWorkGroupDto request) {
        AreaWorkGroup result = new AreaWorkGroup();
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    @PutMapping
    public ResponseEntity<?> updateData(@Valid @RequestBody AreaWorkGroupDto request) {
        AreaWorkGroup result = service.getById(request.getArwgCode());
        return new ResponseEntity<>(service.save(TransactionMapper.mapDtoToEntity(request, result)), HttpStatus.CREATED);
    }
}
