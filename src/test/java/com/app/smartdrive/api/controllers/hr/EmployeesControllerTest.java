package com.app.smartdrive.api.controllers.hr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.app.smartdrive.api.dto.HR.request.EmployeesRequestDto;
import com.app.smartdrive.api.dto.customer.request.CustomerRequestTypeDTO;
import com.app.smartdrive.api.dto.user.request.ProfileRequestDto;
import com.app.smartdrive.api.dto.user.request.UserPhoneRequestDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneDto;
import com.app.smartdrive.api.dto.user.response.UserPhoneIdDto;
import com.app.smartdrive.api.entities.customer.CustomerRequest;
import com.app.smartdrive.api.entities.hr.Employees;
import com.app.smartdrive.api.entities.partner.Partner;
import com.app.smartdrive.api.entities.users.User;
import com.app.smartdrive.api.entities.users.UserPhoneId;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeesControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    EmployeesService employeesService;

    
    

    @BeforeEach
    @Transactional
    public void setUp() {
        employeesRepository.deleteAll();
        userRepository.deleteByUserName("TEST123");
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    User createUser(String test){
        ProfileRequestDto profil = new ProfileRequestDto();
        profil.setUserName(test);
        profil.setUserPassword(test);
        profil.setUserEmail(test);
        profil.setUserNpwp(test);
        profil.setUserNationalId(test);
        profil.setUserFullName(test);

        return userService.createUser(profil);
    }

    Employees create(){
        EmployeesRequestDto employeesRequestDto = new EmployeesRequestDto();
        UserPhoneRequestDto userPhoneDto = new UserPhoneRequestDto();
        userPhoneDto.setUsphPhoneNumber("1223321");
        employeesRequestDto.setEmpName("babibu");
        employeesRequestDto.setEmail("bab@gmail.com");
        employeesRequestDto.setEmpPhone(userPhoneDto);

        Employees employees = employeesService.createEmployee(employeesRequestDto);
        return employeesService.save(employees);
    
    }

    @Test     
    void whenDelete_thenSucces() throws Exception {
        Employees employees = create();

        mockMvc.perform(
                delete("/employees/"+employees.getEmpEntityid())
        ).andExpectAll(
                status().is(204)
        );



    }

   





}
