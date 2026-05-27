package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.EmployeeIdDTO;
import com.employee.reponse.ApiResponse;
import com.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping("/employees")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> addNewEmployeeDetails(@RequestBody EmployeeDTO employeeDto) {
        ApiResponse<EmployeeDTO> response = employeeService.addEmployee(employeeDto);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/employees/{id}")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> getEmployeeDataById(@PathVariable int id) {
        ApiResponse<EmployeeDTO> response = employeeService.fetchEmployeeDataById(id);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/employees/email")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> getEmployeeDataByEmail(@RequestParam String email) {
        ApiResponse<EmployeeDTO> response = employeeService.fetchEmployeeDataByEmail(email);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @PutMapping("/employees")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> updateEmployeeDataById(@RequestParam int id, @RequestBody EmployeeDTO employeeDTO) {
        ApiResponse<EmployeeDTO> response = employeeService.updateEmployeeDataById(id, employeeDTO);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @DeleteMapping("/employees/{id}")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> deleteEmployeeDataById(@PathVariable int id) {
        ApiResponse<EmployeeDTO> response = employeeService.deleteEmployeeDataById(id);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/employees")
    ResponseEntity<@NotNull ApiResponse<List<EmployeeDTO>>> getAllEmployeeData() {
        ApiResponse<List<EmployeeDTO>> response = employeeService.getAllEmployeeData();
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/employees/all")
    ResponseEntity<@NotNull ApiResponse<List<EmployeeIdDTO>>> getAllEmployeeDataWithEmployeeId() {
        ApiResponse<List<EmployeeIdDTO>> response = employeeService.getAllEmployeeDataWithEmployeeId();
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @DeleteMapping("employees/delete/all")
    ResponseEntity<@NotNull ApiResponse<EmployeeDTO>> deleteAllEmployeeDatal() {
        ApiResponse<EmployeeDTO> response = employeeService.deleteAllEmployeeData();
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/get/employeeid")
    ResponseEntity<@NotNull ApiResponse<UUID>> getGeneratedEmployeeId(@RequestParam String email) {
        ApiResponse<UUID> response = employeeService.getEmployeeId(email);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/get/id")
    ResponseEntity<@NotNull ApiResponse<Integer>> getEmployeeId(@RequestParam String email) {
        ApiResponse<Integer> response = employeeService.getPrimaryEmployeeId(email);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }


}
