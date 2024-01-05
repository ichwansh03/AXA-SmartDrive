package com.app.smartdrive.api.services.HR.implementation;

import com.app.smartdrive.api.Exceptions.EntityNotFoundException;
import com.app.smartdrive.api.dto.HR.response.EmployeesJobTypeResponseDto;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.hr.JobType;
import com.app.smartdrive.api.mapper.TransactionMapper;
import com.app.smartdrive.api.repositories.HR.JobTypeRepository;
import com.app.smartdrive.api.services.HR.JobTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobTypeServiceImpl implements JobTypeService {

    private final JobTypeRepository jobTypeRepository;
    @Override
    public EmployeesJobTypeResponseDto getById(String id) {
        JobType jobType = jobTypeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("JobType with id : " + id + " is not found")
        );

        return TransactionMapper.mapEntityToDto(jobType,EmployeesJobTypeResponseDto.class);
    }

    @Override
    public List<EmployeesJobTypeResponseDto> getAll(){
        List<JobType> jobType = jobTypeRepository.findAll();
        return TransactionMapper.mapEntityListToDtoList(jobType,EmployeesJobTypeResponseDto.class);
    }
}
