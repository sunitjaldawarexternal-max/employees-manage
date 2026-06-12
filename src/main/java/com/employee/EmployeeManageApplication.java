package com.employee;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee Microservice: REST API documentation",
                description = "Employee, We Can Perform CURD Operations, Create, Update, Read, Delete",
                version = "v1",
                contact = @Contact(
                        name = "Sunit",
                        email = "sunit.jaldawar.external@atos.ai"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Local Development Server"
                )
        }
)
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class EmployeeManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeManageApplication.class, args);
    }
}
