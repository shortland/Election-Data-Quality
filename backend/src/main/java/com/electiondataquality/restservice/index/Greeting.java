package com.electiondataquality.restservice.index;

public class Greeting {

    private final long requests;

    private final String content;

    public Greeting(long requests, String content) {
        this.requests = requests;
        this.content = content;
    }

    public long getRequests() {
        return requests;
    }

    public String getContent() {
        return content;
    }
}
