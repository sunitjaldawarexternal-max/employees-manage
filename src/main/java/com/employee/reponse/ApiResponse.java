package com.employee.reponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(
        name = "ApiResponse",
        description = "Generic API response wrapper containing status, message, and response data"
)
public class ApiResponse<A> {

    @Schema(
            description = "HTTP status code of the response",
            example = "200"
    )
    private int statusCode;

    @Schema(
            description = "Response message",
            example = "Employee fetched successfully"
    )
    private String message;

    @Schema(
            description = "Response payload"
    )
    private A data;

    public ApiResponse() {

    }
}