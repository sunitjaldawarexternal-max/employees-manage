package com.employee.service;

import com.employee.dto.EmployeeDTO;
import com.employee.entity.Employee;
import com.employee.mapper.MapToEmployee;
import com.employee.mapper.MapToEmployeeDTO;
import com.employee.reponse.ApiResponse;
import com.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private MapToEmployee mapToEmployee;

    @Mock
    private MapToEmployeeDTO mapToEmployeeDTO;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void addEmployee() {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setName("John");
        dto.setDepartment("Test");
        dto.setEmail("Test@gmail.com");
        dto.setSalary(90.00);

        Employee employeeEntity = new Employee(); //stubbing
        employeeEntity.setId(1);
        employeeEntity.setName("John");
        // 🔥 FIX 1: mock mapper (VERY IMPORTANT)
        when(mapToEmployee.mapToEmployee(any(EmployeeDTO.class)))
                .thenReturn(employeeEntity);
        // 🔥 FIX 2: mock repository
        when(employeeRepository.save(any(Employee.class)))
                .thenReturn(employeeEntity);

        ApiResponse<EmployeeDTO> response = employeeService.addEmployee(dto);
        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals("John", response.getData().getName());
    }

    @Test
    void fetchEmployeeDataById() {
        Employee employee=new Employee();
        employee.setId(1);
        employee.setName("sunit");
        employee.setDepartment("IT");
        employee.setEmail("sunit@gmail.com");
        employee.setEmployeeId(UUID.randomUUID());
        employee.setSalary(12000);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Sunit");
        employeeDTO.setDepartment("IT");

        //what to act
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(mapToEmployeeDTO.mapToEmployeeDTO(any(Employee.class))).thenReturn(employeeDTO);

        //what it will return
        ApiResponse<EmployeeDTO> result=employeeService.fetchEmployeeDataById(1);
        assertNotNull(result);
        assertEquals("Sunit",result.getData().getName());
        assertEquals("IT",result.getData().getDepartment());
        assertEquals(200, result.getStatusCode());
        assertEquals("Employee fetched successfully", result.getMessage());
    }

    @Test
    void fetchEmployeeDataByEmail() {
        Employee employee=new Employee();
        employee.setId(1);
        employee.setName("sunit");
        employee.setDepartment("IT");
        employee.setEmail("sunit@gmail.com");
        employee.setEmployeeId(UUID.randomUUID());
        employee.setSalary(12000);

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName("Sunit");
        employeeDTO.setDepartment("IT");
        employeeDTO.setEmail("sunit@gmail.com");

        //what to act
        when(employeeRepository.findByEmail("sunit@gmail.com")).thenReturn(Optional.of(employee));
        when(mapToEmployeeDTO.mapToEmployeeDTO(any(Employee.class))).thenReturn(employeeDTO);

        //what it will return
        ApiResponse<EmployeeDTO> result=employeeService.fetchEmployeeDataByEmail("sunit@gmail.com");
        assertNotNull(result);
        assertEquals("Sunit",result.getData().getName());
        assertEquals("IT",result.getData().getDepartment());
        assertEquals(200, result.getStatusCode());
        assertEquals("Employee fetched successfully", result.getMessage());

    }

    @Test
    void updateEmployeeDataById() {
        Employee employee=new Employee();
        employee.setId(1);
        employee.setName("Ram");
        employee.setDepartment("HR");
        employee.setSalary(90000);

        EmployeeDTO updateDTO=new EmployeeDTO();
        updateDTO.setName("Sunit");
        updateDTO.setDepartment("IT");


        //mockito
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        doNothing().when(mapToEmployee).mapToEmployee(any(EmployeeDTO.class),any(Employee.class));
        doNothing().when(employeeRepository).update(anyInt(),any(Employee.class));

        ApiResponse<EmployeeDTO> response =
                employeeService.updateEmployeeDataById(1, updateDTO);

        assertNotNull(response);
        assertEquals("Sunit", response.getData().getName());
        assertEquals("IT", response.getData().getDepartment());

        verify(employeeRepository)
                .update(anyInt(), any(Employee.class));
        verify(mapToEmployee)
                .mapToEmployee(any(EmployeeDTO.class),
                        any(Employee.class));
        verify(employeeRepository)
                .update(anyInt(), any(Employee.class));
        
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
    void deleteAllEmployeeData() {
    }

    @Test
    void getEmployeeId() {
    }

    @Test
    void getPrimaryEmployeeId() {
    }
}