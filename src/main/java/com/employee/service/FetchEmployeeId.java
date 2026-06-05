package com.employee.service;

import com.employee.exception.EmployeeManagementException;
import io.github.resilience4j.retry.annotation.Retry;
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

    @Retry(name = "fetchEmployeeIdFromReferenceId", fallbackMethod = "fetchEmployeeIdFromReferenceIdFallBack")
    public UUID fetchEmployeeIdFromReferenceId(String referenceId) {
        log.info("Fetching EmployeeId For Refernce id {}", referenceId);
        try {
            return restClient.get()
                    .uri(referenceUrl + "{referenceId}", referenceId)
                    .retrieve()
                    .body(UUID.class);
        } catch (Exception e) {
            log.error("Not Able To Retrieve refernce id : {}", e.getMessage());
            throw new EmployeeManagementException("Exception occurred while connecting to RestClient");
        }
    }

    public UUID fetchEmployeeIdFromReferenceIdFallBack(String referenceId, Throwable throwable) {
        log.info("Reference Id Service Is unavailable After 5 Retry Attempts, Fall Back Method Returning Null");
        return null;
    }
}
