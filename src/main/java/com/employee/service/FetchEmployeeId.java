package com.employee.service;

import com.employee.exception.EmployeeManagementException;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchEmployeeId {

    @Autowired
    private final RestClient restClient;
    @Autowired
    private final RedisTemplate<String, UUID> redisTemplate;


    @Value("${reference.service.url}")
    private String referenceUrl;

    //    @Cacheable(value="employeeIdsByReferenceId",key="#referenceID")
    @Retry(name = "fetchEmployeeIdFromReferenceId", fallbackMethod = "fetchEmployeeIdFromReferenceIdFallBack")
    public UUID fetchEmployeeIdFromReferenceId(String referenceID) {

        String cacheKey = "employeeIdByReferenceId::" + referenceID;
        UUID cachedEmployeeId = redisTemplate.opsForValue().get(cacheKey);
        if (cachedEmployeeId != null) return cachedEmployeeId;


        log.info("Fetching EmployeeId For Refernce id {}", referenceID);
        try {
            UUID employeeId = restClient.get()
                    .uri(referenceUrl + "{referenceId}", referenceID)
                    .retrieve()
                    .body(UUID.class);
            redisTemplate.opsForValue().set(
                    cacheKey,
                    employeeId);
            return employeeId;
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
