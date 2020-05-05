package com.electiondataquality.types.responses;

import com.electiondataquality.types.responses.enums.API_STATUS;

public class ResponseGen {
    public static ApiResponse create(Object content) {
        return new ApiResponse(content, "ok");
    }

    public static ApiResponse create(String status, Object content) {
        return new ApiResponse(content, status);
    }

    public static ApiResponse create(API_STATUS status, Object content) {
        return new ApiResponse(content, status);
    }
}
