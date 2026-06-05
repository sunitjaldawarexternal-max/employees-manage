package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.EmployeeIdDTO;
import com.employee.reponse.ApiResponse;
import com.employee.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "CRUD REST APIs for Employee Management",
        description = "APIs to create, retrieve, update, and delete employee records in the Employee Management Service"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @Operation(
            summary = "Create Employee",
            description = "Creates a new employee record in the Employee Management Service"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Employee created successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee already exists"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @PostMapping("/employees")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> addNewEmployeeDetails(@RequestBody EmployeeDTO employeeDto) {
        ApiResponse<EmployeeDTO> response = employeeService.addEmployee(employeeDto);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Get Employee By ID",
            description = "Retrieves employee details using the employee ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee Data Fetched Successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/employees/{id}")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> getEmployeeDataById(@PathVariable int id) {
        ApiResponse<EmployeeDTO> response = employeeService.fetchEmployeeDataById(id);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Get Employee By Email",
            description = "Retrieves employee details using the employee email address"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee Data Fetched Successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/employees/email")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> getEmployeeDataByEmail(@RequestParam String email) {
        ApiResponse<EmployeeDTO> response = employeeService.fetchEmployeeDataByEmail(email);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Update Employee",
            description = "Updates an existing employee record using the employee ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee Details Updated Successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @PutMapping("/employees")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> updateEmployeeDataById(@RequestParam int id, @RequestBody EmployeeDTO employeeDTO) {
        ApiResponse<EmployeeDTO> response = employeeService.updateEmployeeDataById(id, employeeDTO);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Delete Employee By ID",
            description = "Deletes an employee record using the employee ID"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee Data Deleted Successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @DeleteMapping("/employees/{id}")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> deleteEmployeeDataById(@PathVariable int id) {
        ApiResponse<EmployeeDTO> response = employeeService.deleteEmployeeDataById(id);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Get All Employees",
            description = "Retrieves all employee records from the Employee Management Service"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "All employee records retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/employees")
    ResponseEntity<@NotNull ApiResponse<List<EmployeeDTO>>> getAllEmployeeData() {
        ApiResponse<List<EmployeeDTO>> response = employeeService.getAllEmployeeData();
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Get All Employees With Employee ID",
            description = "Retrieves all employee records along with generated employee UUIDs from the Reference Service"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee records and employee IDs retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/employees/all")
    ResponseEntity<@NotNull ApiResponse<List<EmployeeIdDTO>>> getAllEmployeeDataWithEmployeeId() {
        ApiResponse<List<EmployeeIdDTO>> response = employeeService.getAllEmployeeDataWithEmployeeId();
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Delete All Employees",
            description = "Deletes all employee records from the Employee Management Service"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "All employee records deleted successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @DeleteMapping("/employees/delete/all")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> deleteAllEmployeeDatal() {
        ApiResponse<EmployeeDTO> response = employeeService.deleteAllEmployeeData();
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Get Employee UUID",
            description = "Retrieves the generated employee UUID from the Reference Service using the employee email address"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee UUID retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/get/employeeid")
    ResponseEntity<@NotNull ApiResponse<UUID>> getGeneratedEmployeeId(@RequestParam String email) {
        ApiResponse<UUID> response = employeeService.getEmployeeId(email);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @Operation(
            summary = "Get Employee Primary ID",
            description = "Retrieves the primary employee ID from the Employee Management Service using the employee email address"
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Employee ID retrieved successfully"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/get/id")
    ResponseEntity<@NotNull ApiResponse<Integer>> getEmployeeId(@RequestParam String email) {
        ApiResponse<Integer> response = employeeService.getPrimaryEmployeeId(email);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

}
