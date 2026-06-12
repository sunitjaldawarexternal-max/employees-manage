package com.employee.producer;

import com.employee.dto.EmployeeDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeProducer {

    private final KafkaTemplate<String, EmployeeDTO> kafkaTemplate;

    EmployeeDTO employeeDTO = EmployeeDTO.builder()
            .referenceId("KAFKA01")
            .email("kafka@gmail.com")
            .name("Rahul")
            .salary(10000.0)
            .department("HR")
            .build();

    @PostConstruct
    public void sendHardcodedData() {
        try {
            kafkaTemplate.send(
                    "employee-topic",
                    employeeDTO);
        } catch (Exception e) {
            log.info("Failed Connection With Kafka: {}",e.getMessage());
        }

    }


    public void producerKafkaEmployeeDTO(EmployeeDTO employeeDto) {
        EmployeeDTO kafkaModifiedEmployeeDTO = EmployeeDTO.builder()
                .salary(employeeDto.getSalary())
                .email(employeeDto.getEmail())
                .name(employeeDto.getName())
                .department(employeeDto.getDepartment())
                .referenceId(employeeDto.getReferenceId())
                .build();

        kafkaTemplate.send(
                "employee-topic",
                kafkaModifiedEmployeeDTO);
    }
}