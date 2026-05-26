package com.employee.mapper;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.EmployeeIdDTO;
import com.employee.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MapToEmployeeDTO {
    public EmployeeDTO mapToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setName(employee.getName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setDepartment(employee.getDepartment());
        employeeDTO.setSalary(employee.getSalary());

        return employeeDTO;
    }

    public EmployeeIdDTO mapToEmployeeIdDto(Employee employee) {
        EmployeeIdDTO employeeIdDTO = new EmployeeIdDTO();
        employeeIdDTO.setEmployeeId(employee.getEmployeeId());
        employeeIdDTO.setName(employee.getName());
        employeeIdDTO.setEmail(employee.getEmail());
        employeeIdDTO.setDepartment(employee.getDepartment());
        employeeIdDTO.setSalary(employee.getSalary());

        return employeeIdDTO;
    }

}

