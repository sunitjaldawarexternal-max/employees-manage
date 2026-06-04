package com.employee.mapper;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.EmployeeIdDTO;
import com.employee.entity.Employee;
import com.employee.service.FetchEmployeeId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class MapToEmployee {
    @Autowired
    private FetchEmployeeId fetchEmployeeId;

    public Employee mapToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        fetchEmployeeId.fetchEmployeeIdFromReferenceId(employeeDTO.getReferenceId());
        employee.setReferenceId(employeeDTO.getReferenceId());
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setSalary(employeeDTO.getSalary());
        return employee;
    }

    public void mapToEmployee(EmployeeDTO employeeDTO, Employee employee) {
        employee.setName(
                Optional.ofNullable(employeeDTO.getName())
                        .orElse(employee.getName())
        );

        employee.setEmail(
                Optional.ofNullable(employeeDTO.getEmail())
                        .orElse(employee.getEmail())
        );

        employee.setDepartment(
                Optional.ofNullable(employeeDTO.getDepartment())
                        .orElse(employee.getDepartment())
        );

        employee.setSalary(
                Optional.ofNullable(employeeDTO.getSalary())
                        .orElse(employee.getSalary())
        );

        employee.setReferenceId(
                Optional.ofNullable(employeeDTO.getReferenceId())
                        .orElse(employee.getReferenceId())
        );

    }


}

