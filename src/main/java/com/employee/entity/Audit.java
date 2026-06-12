package com.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit_table")
public class Audit {
    @Id
    @Column(name = "audit_id")
    private UUID auditId;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Lob
    @Column(name = "payload")
    private String payload;

    @Column(name = "status")
    private String status; // PENDING, SUCCESS, FAILURE
}
