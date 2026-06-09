package com.employee.processor;

import com.employee.constant.EmployeeConstant;
import com.employee.exception.EmployeeManagementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchEmployeeProcessor {

    @Autowired
    private final RestClient restClient;

    @Value("${reference.service.url}")
    private String referenceUrl;

    public UUID fetchEmployeeId(String referenceID){
        log.info("Processing Request");

        try {
            log.info("Fetching EmployeeID For Refernce ID {}", referenceID);
            UUID employeeID = restClient.get()
                    .uri(referenceUrl + "{referenceId}", referenceID)
                    .retrieve()
                    .body(UUID.class);
            return employeeID;

        } catch (Exception e) {
            log.error("Not Able To Retrieve refernce ID : {}", e.getMessage());
            throw new EmployeeManagementException(EmployeeConstant.REST_CLIENT_EXCEPTION);
        }

    }
}
