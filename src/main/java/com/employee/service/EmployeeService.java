package com.employee.service;

import com.employee.constant.EmployeeConstant;
import com.employee.dto.EmployeeDTO;
import com.employee.dto.EmployeeIdDTO;
import com.employee.entity.Employee;
import com.employee.exception.NotFoundException;
import com.employee.mapper.MapToEmployee;
import com.employee.mapper.MapToEmployeeDTO;
import com.employee.reponse.ApiResponse;
import com.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private MapToEmployee mapToEmployee;
    @Autowired
    private MapToEmployeeDTO mapToEmployeeDTO;

    public ApiResponse<EmployeeDTO> addEmployee(EmployeeDTO employeeDto) {
        try {
            boolean isEmailIdPresent = employeeRepository.existsByEmail(employeeDto.getEmail());
            if (isEmailIdPresent) {
                log.info("Employee creation failed. Email already exists");
                return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMAIL_ALREADY_EXISTS, employeeDto);
            }

            Employee employee = mapToEmployee.mapToEmployee(employeeDto);
            employeeRepository.save(employee);
            log.info("Employee created successfully");
            return new ApiResponse<>(EmployeeConstant.STATUS_201, EmployeeConstant.EMPLOYEE_CREATED, employeeDto);

        } catch (Exception e) {
            log.error("Error occurred while adding : {}", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, employeeDto);
        }
    }

    public ApiResponse<EmployeeDTO> fetchEmployeeDataById(int id) {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> {
                        log.info("Employee Not Found. With Employee ID : {}.", id);
                        return new NotFoundException(EmployeeConstant.EMPLOYEE_NOT_FOUND);
                    });

            EmployeeDTO employeeDto = mapToEmployeeDTO.mapToEmployeeDTO(employee);
            log.info("Employee details fetched by id {}", id);
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_FETCHED, employeeDto);
        } catch (Exception e) {
            log.error("Error occurred while fetching by id : {}", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);

        }
    }

    public ApiResponse<EmployeeDTO> fetchEmployeeDataByEmail(String email) {
        try {
            Employee employee = employeeRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        log.info("Employee Not Found. With this email : {}.", email);
                        return new NotFoundException("Employee Not Found.With this email");
                    });

            EmployeeDTO employeedto = mapToEmployeeDTO.mapToEmployeeDTO(employee);
            log.info("Employee details fetched by email {}", email);
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_FETCHED, employeedto);
        } catch (Exception e) {
            log.error("Error occurred while fetching by email : {}", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);
        }
    }

    public ApiResponse<EmployeeDTO> updateEmployeeDataById(int id, EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeRepository.findById(id)
                    .orElseThrow(() -> {
                        log.info("Employee Not Found. Employee ID : {}.", id);
                        return new NotFoundException(EmployeeConstant.EMPLOYEE_NOT_FOUND);
                    });

            mapToEmployee.mapToEmployee(employeeDTO, employee);
            employeeRepository.update(id, employee);

            log.info("Employee id:{} Update Successfully", id);
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_UPDATED, employeeDTO);
        } catch (Exception e) {
            log.error("Error occurred while Updating Employee Detail by id : {}", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);
        }
    }

    @Transactional
    public ApiResponse<EmployeeDTO> deleteEmployeeDataById(int id) {
        try {
            employeeRepository.deleteById(id);
            log.info("Employee Deleted Successful by id {}", id);
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_DELETED, null);
        } catch (Exception e) {
            log.error("Error occurred while Deleting by id : {}", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);
        }

    }

    public ApiResponse<List<EmployeeDTO>> getAllEmployeeData() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            boolean isEmployeePresent = employees.isEmpty();
            if (isEmployeePresent) {
                log.info("Employee Not Found");
                return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_NOT_FOUND, null);
            }
            List<EmployeeDTO> employeeDTO = employees.stream().map(mapToEmployeeDTO::mapToEmployeeDTO).toList();
            log.info("Displaying all Employees Detail");
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_FETCHED, employeeDTO);

        } catch (Exception e) {
            log.error("Error occurred while fetching all employee data : {}", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);

        }
    }


    public ApiResponse<List<EmployeeIdDTO>> getAllEmployeeDataWithEmployeeId() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            boolean isEmployeePresent = employees.isEmpty();
            if (isEmployeePresent) {
                log.warn("Their Is No Employee Detail, Complete Empty");
                return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_NOT_FOUND, null);
            }
            List<EmployeeIdDTO> employeesIdDto = employees.stream().map(mapToEmployeeDTO::mapToEmployeeIdDto).toList();
            log.info("Displaying all Employee Details");
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_FETCHED, employeesIdDto);
        } catch (Exception e) {
            log.error("Error occurred while fetching all employees data : {}", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);
        }
    }


    public ApiResponse<EmployeeDTO> deleteAllEmployeeData() {
        try {
            employeeRepository.deleteAll();
            log.info("Deleted All Employees Details");
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_DELETED, null);
        } catch (Exception e) {
            log.error("Error Occurred While Deleting Whole Table : {} ", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);
        }
    }

    public ApiResponse<UUID> getEmployeeId(String email) { //get
        try {
            Employee employee = employeeRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        log.info("Employee Not Found. With email : {}.", email);
                        return new NotFoundException(EmployeeConstant.EMPLOYEE_NOT_FOUND);
                    });
            if (employee == null) {
                log.warn("Employee with Email: {} Not Found", email);
                return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.EMPLOYEE_NOT_FOUND, null);
            }
            UUID employeeId = employee.getEmployeeId();
            log.info("Employee Id Fetched Successfully : {}", employeeId);
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.MESSAGE_200, employeeId);
        } catch (Exception e) {
            log.error("Error Occurred While Knowing Employee Id : {} ", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);
        }
    }

    public ApiResponse<Integer> getPrimaryEmployeeId(String email) {
        try {
            Employee employee = employeeRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        log.info("Employee Not Found, With Email : {}.", email);
                        return new NotFoundException(EmployeeConstant.EMPLOYEE_NOT_FOUND);
                    });
            int id = employee.getId();
            log.info("Employee Id Fetched Successfully: {}", id);
            return new ApiResponse<>(EmployeeConstant.STATUS_200, EmployeeConstant.MESSAGE_200, id);

        } catch (Exception e) {
            log.error("Error Occurred While Getting Id : {} ", e.getMessage());
            return new ApiResponse<>(EmployeeConstant.STATUS_500, EmployeeConstant.MESSAGE_500, null);
        }
    }


}
