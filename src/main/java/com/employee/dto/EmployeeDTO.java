package com.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "Employee",
        description = "Schema To Hold Employee Information"
)
public class EmployeeDTO {
    @Schema(
            description = "Employee Name",
            example = "Sunit Jaldawar"

    )
    private String name;
    @Schema(
            description = "Employee Email",
            example = "SunitJaldawar@gmail.com"

    )
    private String email;

    @Schema(
            description = "Employee Department",
            example = "Information Technology"

    )
    private String department;

    @Schema(
            description = "Employee Salary",
            example = "1000"

    )
    private Double salary;

    @Schema(
            description = "Reference ID associated with the employee",
            example = "IT001"

    )
    private String referenceId;

}
