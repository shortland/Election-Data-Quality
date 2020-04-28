package com.electiondataquality.types.errors;

import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class ErrorJ {

    private String errorString;

    private String messageString;

    public ErrorJ(String error) {
        this.errorString = String.format("%s", error);
    }

    @JsonIgnore
    public String getErrorString() {
        return this.errorString;
    }

    public void setErrorString(String error) {
        this.errorString = error;
    }

    public void setMessageString(String message) {
        this.messageString = message;
    }

    /**
     * Method is used by jax/spring serialization to create the Json error object.
     */
    public HashMap<String, String> getResponse() {
        HashMap<String, String> errorSet = new HashMap<String, String>();

        if (errorString == null || errorString.length() == 0) {
            errorSet.put("status", "ok");

            if (messageString != null && messageString.length() > 0) {
                errorSet.put("message", this.messageString);
            }

            return errorSet;
        }

        errorSet.put("status", "error");
        errorSet.put("error", this.errorString);

        return errorSet;
    }
}
