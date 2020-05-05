package com.electiondataquality.restservice.index;

public class Greeting {

    private final long requests;

    private final String message;

    public Greeting(long requests, String message) {
        this.requests = requests;
        this.message = message;
    }

    public long getRequests() {
        return requests;
    }

    public String getMessage() {
        return message;
    }
}
