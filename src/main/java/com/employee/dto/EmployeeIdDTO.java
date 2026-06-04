package com.employee.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeIdDTO {
    private String name;
    private String email;
    private String department;
    private double salary;
    private String ReferenceId;
    /**
     * Fetched By Reference Service
     */
    private UUID EmployeeId;
}
