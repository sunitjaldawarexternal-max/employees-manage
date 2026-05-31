package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.reponse.ApiResponse;
import com.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @SneakyThrows
    @Test
    void addNewEmployeeDetails() {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDepartment("IT");
        employeeDTO.setEmail("sunit@gmail.com");

        ApiResponse<EmployeeDTO> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());

        when(employeeService.addEmployee(any(EmployeeDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeDTO)))
                .andExpect(status().isCreated());

    }

    @Test
    void getEmployeeDataById() {
    }

    @Test
    void getEmployeeDataByEmail() {
    }

    @Test
    void updateEmployeeDataById() {
    }

    @Test
    void deleteEmployeeDataById() {
    }

    @Test
    void getAllEmployeeData() {
    }

    @Test
    void getAllEmployeeDataWithEmployeeId() {
    }

    @Test
    void deleteAllEmployeeDatal() {
    }

    @Test
    void getGeneratedEmployeeId() {
    }

    @Test
    void getEmployeeId() {
    }
}