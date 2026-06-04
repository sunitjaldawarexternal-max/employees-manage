package com.employee.service;

import com.employee.exception.EmployeeManagementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchEmployeeId {

    @Autowired
    private final RestClient restClient;

    @Value("${reference.service.url}")
    private String referenceUrl;

    public UUID fetchEmployeeIdFromReferenceId(String referenceId) {
        try {
            return restClient.get()
                    .uri(referenceUrl + "{referenceId}", referenceId)
                    .retrieve()
                    .body(UUID.class);
        } catch (Exception e) {
            log.error("Not Able To Retrieve refernce id : {}",e.getMessage());
            throw new EmployeeManagementException("Exception occurred while connecting to RestClient");
        }
    }
}
