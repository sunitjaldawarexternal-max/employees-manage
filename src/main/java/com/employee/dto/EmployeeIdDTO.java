package com.employee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(
        name = "EmployeeId",
        description = "Schema To Hold Employee Information Along with EmployeeId"
)
public class EmployeeIdDTO { @Schema(
        description = "Employee Name",
        example = "Ram Sita"

)
private String name;
    @Schema(
            description = "Employee Email",
            example = "RamSita@gmail.com"

    )
    private String email;

    @Schema(
            description = "Employee Department",
            example = "Ram Sita"

    )
    private String department;

    @Schema(
            description = "Employee Salary",
            example = "90000"

    )
    private Double salary;

    @Schema(
            description = "Reference ID associated with the employee",
            example = "IT001"

    )
    private String referenceId;

    @Schema(
            description = "EmployeeId Will Fetch from reference serve",
            example = "uefe90-wudjx98-adueni-932324"

    )
    private UUID employeeId;
}
