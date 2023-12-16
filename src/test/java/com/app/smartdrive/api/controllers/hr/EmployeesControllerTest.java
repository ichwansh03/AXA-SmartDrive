package com.app.smartdrive.api.controllers.hr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.app.smartdrive.api.controllers.HR.EmployeesController;
import com.app.smartdrive.api.dto.HR.response.EmployeesResponseDto;
import com.app.smartdrive.api.repositories.HR.EmployeesRepository;
import com.app.smartdrive.api.repositories.users.UserRepository;
import com.app.smartdrive.api.services.HR.EmployeesService;
import com.app.smartdrive.api.services.users.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @InjectMocks
    private EmployeesController employeesController;

    @Autowired
    EmployeesRepository employeesRepository;

    @Mock
    EmployeesService employeesService;

    
    @Test
    void getAllEmployees() {
        EmployeesResponseDto employee1 = new EmployeesResponseDto();
        employee1.setEmpName("bara");

        EmployeesResponseDto employee2 = new EmployeesResponseDto();
        employee2.setEmpName("jane");

        List<EmployeesResponseDto> employeesList = Arrays.asList(employee1, employee2);
        when(employeesService.getAllDto()).thenReturn(employeesList);

        List<EmployeesResponseDto> result = employeesController.getAllEmployees();


        assertEquals(2, result.size());
        assertEquals("bara", result.get(0).getEmpName());
        assertEquals("jane", result.get(1).getEmpName());
    }
    

    // @BeforeEach
    // @Transactional
    // public void setUp() {
    //     employeesRepository.deleteAll();
    //     userRepository.deleteByUserName("TEST123");
    //     objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    // }

    // User createUser(String test){
    //     ProfileRequestDto profil = new ProfileRequestDto();
    //     profil.setUserName(test);
    //     profil.setUserPassword(test);
    //     profil.setUserEmail(test);
    //     profil.setUserNpwp(test);
    //     profil.setUserNationalId(test);
    //     profil.setUserFullName(test);

    //     return userService.createUser(profil);
    // }

    // Employees create(){
    //     EmployeesRequestDto employeesRequestDto = new EmployeesRequestDto();
    //     UserPhoneRequestDto userPhoneDto = new UserPhoneRequestDto();
    //     userPhoneDto.setUsphPhoneNumber("1223321");
    //     employeesRequestDto.setEmpName("babibu");
    //     employeesRequestDto.setEmail("bab@gmail.com");
    //     employeesRequestDto.setEmpPhone(userPhoneDto);

    //     Employees employees = employeesService.createEmployee(employeesRequestDto);
    //     return employeesService.save(employees);
    
    // }

    // @Test     
    // void whenDelete_thenSucces() throws Exception {
    //     Employees employees = create();

    //     mockMvc.perform(
    //             delete("/employees/"+employees.getEmpEntityid())
    //     ).andExpectAll(
    //             status().is(204)
    //     );



    // }

   





}
