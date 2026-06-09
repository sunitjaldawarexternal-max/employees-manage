package com.employee.service;

import com.employee.constant.EmployeeConstant;
import com.employee.processor.FetchEmployeeProcessor;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FetchEmployeeId {

    @Autowired
    private final FetchEmployeeProcessor fetchEmployeeProcessor;
    @Autowired
    private final RedisTemplate<String, UUID> redisTemplate;

    //    @Cacheable(value="employeeIdsByReferenceId",key="#referenceID")
    @Retry(name = EmployeeConstant.RETRY, fallbackMethod = EmployeeConstant.RETRY_FALLBACK)
    public UUID fetchEmployeeIdFromReferenceId(String referenceID) {
        String cacheKey = EmployeeConstant.CACHE_KEY_PREFIX + referenceID;
        UUID cachedEmployeeId = redisTemplate.opsForValue().get(cacheKey);
        if (cachedEmployeeId != null) {
            log.info("EmployeeId retrieved from Cache Memory : {}",cachedEmployeeId);
            return cachedEmployeeId;
        } else {
            UUID employeeID = fetchEmployeeProcessor.fetchEmployeeId(referenceID);
            log.info("Adding EmployeeId to Cache : {}",employeeID);
            redisTemplate.opsForValue().set(cacheKey, employeeID);
            return employeeID;
        }
    }

    public UUID fetchEmployeeIdFromReferenceIdFallBack(String referenceId, Throwable throwable) {
        log.info("Reference Id Service Is unavailable After 5 Retry Attempts, Fall Back Method Returning Null");
        return null;
    }

}
