package com.example.umc.global.security.util;

import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SecurityResponseUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private SecurityResponseUtil() {
    }

    public static void writeErrorResponse(
            HttpServletResponse response,
            BaseErrorCode code
    ) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(code.getStatus().value());

        ApiResponse<Void> errorResponse = ApiResponse.onFailure(code, null);
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
