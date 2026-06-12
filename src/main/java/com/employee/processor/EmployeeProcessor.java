package com.employee.processor;

import com.employee.dto.EmployeeDTO;
import com.employee.dto.EmployeeIdDTO;
import com.employee.entity.Employee;
import com.employee.mapper.MapToEmployee;
import com.employee.mapper.MapToEmployeeDTO;
import com.employee.service.FetchEmployeeId;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class EmployeeProcessor {

    @Autowired
    private FetchEmployeeId fetchEmployeeId;
    @Autowired
    private MapToEmployeeDTO mapToEmployeeDTO;
    @Autowired
    private MapToEmployee mapToEmployee;


    public Employee maptoEmployeeProcessor(EmployeeDTO employeeDTO) {
        Employee employee = mapToEmployee.mapToEmployee(employeeDTO);
        fetchEmployeeId.fetchEmployeeIdFromReferenceId(employeeDTO.getReferenceId());
        return employee;
    }


    public EmployeeIdDTO EmployeeIdDTOProcessor(Employee employee) {
        EmployeeIdDTO employeeIdDTO = mapToEmployeeDTO.mapToEmployeeIdDto(employee);
        UUID employeeID = fetchEmployeeId.fetchEmployeeIdFromReferenceId(employee.getReferenceId());
        employeeIdDTO.setEmployeeId(employeeID);
        return employeeIdDTO;

    }

    public UUID fetchEmployeeIdProcessor(String referenceId) {
        return fetchEmployeeId.fetchEmployeeIdFromReferenceId(referenceId);
    }


}
