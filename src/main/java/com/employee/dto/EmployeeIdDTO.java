package com.employee.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeIdDTO {
    private UUID employeeId;
    private String name;
    private String email;
    private String department;
    private double salary;
}
