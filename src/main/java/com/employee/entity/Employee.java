package com.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "employee_id")
    private UUID employeeId;

    private String name;

    private String email;

    private String department;

    private double salary;
}
