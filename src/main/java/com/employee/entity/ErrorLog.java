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
@Table(name = "error_table")
public class ErrorLog {
    @Id
    @Column(name = "error_id", nullable = false, updatable = false)
    private UUID errorId;

    @Column(name = "audit_id", nullable = false)
    private UUID auditId;

    @Lob
    @Column(name = "payload", nullable = false)
    private String payload;

    @Column(name = "error_message", length = 1000)
    private String errorMessage;
}
