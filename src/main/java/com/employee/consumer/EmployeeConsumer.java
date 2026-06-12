package com.employee.consumer;

import com.employee.dto.EmployeeDTO;
import com.employee.entity.Audit;
import com.employee.entity.ErrorLog;
import com.employee.reponse.ApiResponse;
import com.employee.repository.AuditRepository;
import com.employee.repository.ErrorLogRepository;
import com.employee.service.EmployeeService;
import com.employee.service.FetchEmployeeId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class EmployeeConsumer {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuditRepository auditRepository;
    @Autowired
    private ErrorLogRepository errorLogRepository;
    @Autowired
    private FetchEmployeeId fetchEmployeeId;

    @KafkaListener(topics = "employee-topic")
    public void consume(EmployeeDTO employeeDTO) throws JsonProcessingException {
        String payload = objectMapper.writeValueAsString(employeeDTO);
        Audit audit = Audit.builder()
                .auditId(UUID.randomUUID())
                .employeeId(fetchEmployeeId.fetchEmployeeIdFromReferenceId(employeeDTO.getReferenceId()))
                .payload(payload)
                .status("PENDING")
                .build();
        auditRepository.save(audit);
        log.info("Kafka message received | referenceId: {}", employeeDTO.getReferenceId());

        try {
            ApiResponse<EmployeeDTO>response = employeeService.addEmployee(employeeDTO);
            if(response.getStatusCode()==200){
                throw new RuntimeException("Duplicate Email Found");
            }
            audit.setStatus("SUCCESS");
            auditRepository.save(audit);
            log.info("Kafka processing completed successfully, auditId: {}", audit.getAuditId());
        } catch (Exception e) {
            ErrorLog error = ErrorLog.builder()
                    .errorId(UUID.randomUUID())
                    .auditId(audit.getAuditId())
                    .payload(audit.getPayload())
                    .errorMessage(e.getMessage())
                    .build();
            errorLogRepository.save(error);
            audit.setStatus("FAILURE");
            auditRepository.save(audit);
            log.info("Kafka message moved to error-table, auditId: {}", audit.getAuditId());
        }
    }


}