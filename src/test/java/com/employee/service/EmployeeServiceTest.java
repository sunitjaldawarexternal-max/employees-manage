package com.employee.service;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.EmployeeIdDTO;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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
        Employee employee =new Employee();
        employee.setSalary(10000);
        employee.setId(1);
        employee.setEmployeeId(UUID.randomUUID());
        employee.setEmail("sunit@gmail.com");
        employee.setDepartment("IT");

        doNothing().when(employeeRepository).deleteById(1);

        ApiResponse<EmployeeDTO>response= employeeService.deleteEmployeeDataById(1);
        assertNotNull(response);
        assertNull(response.getData());

        verify(employeeRepository).deleteById(1);

    }

    @Test
    void getAllEmployeeData() {
        Employee employee=new Employee();
        employee.setSalary(10000);
        employee.setId(1);
        employee.setEmployeeId(UUID.randomUUID());
        employee.setEmail("sunit@gmail.com");
        employee.setDepartment("IT");

        Employee employee1=new Employee();
        employee1.setSalary(12000);
        employee1.setId(2);
        employee1.setEmployeeId(UUID.randomUUID());
        employee1.setEmail("sunita@gmail.com");
        employee1.setDepartment("IT");

        List<Employee> employees = Arrays.asList(employee, employee1);

        EmployeeDTO employeeDTO=new EmployeeDTO();
        employeeDTO.setDepartment("IT");
        employeeDTO.setEmail("sunit@gmail.com");

        EmployeeDTO employeeDTO1=new EmployeeDTO();
        employeeDTO1.setDepartment("IT");
        employeeDTO1.setEmail("sunita@gmail.com");

        when(employeeRepository.findAll()).thenReturn(employees);
        when(mapToEmployeeDTO.mapToEmployeeDTO(employee)).thenReturn(employeeDTO);
        when(mapToEmployeeDTO.mapToEmployeeDTO(employee)).thenReturn(employeeDTO1);

        ApiResponse<List<EmployeeDTO>> response=employeeService.getAllEmployeeData();

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals("sunita@gmail.com",
                response.getData().get(0).getEmail());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getAllEmployeeDataWithEmployeeId() {
        Employee employee=new Employee();
        employee.setSalary(10000);
        employee.setId(1);
        employee.setEmployeeId(UUID.randomUUID());
        employee.setEmail("sunit@gmail.com");
        employee.setDepartment("IT");

        Employee employee1=new Employee();
        employee1.setSalary(12000);
        employee1.setId(2);
        employee1.setEmployeeId(UUID.randomUUID());
        employee1.setEmail("sunita@gmail.com");
        employee1.setDepartment("IT");

        List<Employee> employees = Arrays.asList(employee, employee1);

        EmployeeIdDTO employeeIdDTO =new EmployeeIdDTO();
        employeeIdDTO.setDepartment("IT");
        employeeIdDTO.setEmail("sunit@gmail.com");

        EmployeeIdDTO employeeIdDTO1 =new EmployeeIdDTO();
        employeeIdDTO1.setDepartment("IT");
        employeeIdDTO1.setEmail("sunita@gmail.com");

        when(employeeRepository.findAll()).thenReturn(employees);
        when(mapToEmployeeDTO.mapToEmployeeIdDto(employee)).thenReturn(employeeIdDTO);
        when(mapToEmployeeDTO.mapToEmployeeIdDto(employee)).thenReturn(employeeIdDTO1);

        ApiResponse<List<EmployeeIdDTO>> response=employeeService.getAllEmployeeDataWithEmployeeId();

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals("sunita@gmail.com",
                response.getData().get(0).getEmail());

        verify(employeeRepository, times(1)).findAll();

    }

    @Test
    void deleteAllEmployeeData() {
        Employee employee=new Employee();
        employee.setSalary(10000);
        employee.setId(1);
        employee.setEmployeeId(UUID.randomUUID());
        employee.setEmail("sunit@gmail.com");
        employee.setDepartment("IT");

        Employee employee1=new Employee();
        employee1.setSalary(12000);
        employee1.setId(2);
        employee1.setEmployeeId(UUID.randomUUID());
        employee1.setEmail("sunita@gmail.com");
        employee1.setDepartment("IT");

        doNothing().when(employeeRepository).deleteAll();

        ApiResponse<EmployeeDTO>response = employeeService.deleteAllEmployeeData();

        assertNotNull(response);
        assertNull(response.getData());

        verify(employeeRepository).deleteAll();
    }

    @Test
    void getEmployeeId() {
        Employee employee=new Employee();
        employee.setSalary(10000);
        employee.setId(1);
        employee.setEmployeeId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        employee.setEmail("sunit@gmail.com");
        employee.setDepartment("IT");

        when(employeeRepository.findByEmail("sunit@gmail.com")).thenReturn(Optional.of(employee));

        ApiResponse<UUID> response = employeeService.getEmployeeId("sunit@gmail.com");

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), response.getData());

    }

    @Test
    void getPrimaryEmployeeId() {
        Employee employee=new Employee();
        employee.setSalary(10000);
        employee.setId(1);
        employee.setEmployeeId(UUID.randomUUID());
        employee.setEmail("sunit@gmail.com");
        employee.setDepartment("IT");

        when(employeeRepository.findByEmail("sunit@gmail.com")).thenReturn(Optional.of(employee));

        ApiResponse<Integer> response = employeeService.getPrimaryEmployeeId("sunit@gmail.com");

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(1, response.getData());

    }
}