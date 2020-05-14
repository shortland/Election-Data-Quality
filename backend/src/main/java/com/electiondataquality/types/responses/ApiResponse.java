package com.electiondataquality.types.responses;

import java.util.Map;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.electiondataquality.types.responses.enums.API_STATUS;

@JsonIgnoreProperties
public class ApiResponse {

    String status;

    Object content;

    public ApiResponse(Object content, API_STATUS status) {
        this.status = status.name().toLowerCase();
        this.content = content;
    }

    public ApiResponse(Object content, String status) {
        this.status = status;
        this.content = content;
    }

    /**
     * Method is used by jax/spring serialization to create the Json error object.
     */
    @JsonValue
    public Map<String, Object> getResponse() {
        Map<String, Object> response = new HashMap<>();

        response.put("status", this.status);
        response.put("content", this.content);

        return response;
    }
}
