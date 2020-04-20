package com.electiondataquality.types.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class ErrorGen {
    public static ErrorJ create(String error) {
        return new ErrorJ(error);
    }

    public static ErrorJ ok() {
        return new ErrorJ("");
    }
}
