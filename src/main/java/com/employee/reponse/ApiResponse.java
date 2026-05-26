package com.employee.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<A> {
    private int statusCode;
    private String message;
    private A data;
}