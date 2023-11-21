package com.app.smartdrive.api.repositories.HR;

import org.springframework.data.jpa.repository.JpaRepository;


import com.app.smartdrive.api.entities.hr.JobType;

public interface JobTypeRepository extends JpaRepository<JobType,String>{
    
}
