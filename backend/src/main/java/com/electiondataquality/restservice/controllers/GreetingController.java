package com.electiondataquality.restservice.controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electiondataquality.restservice.index.Greeting;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s! Please specify an endpoint.";

    private final AtomicLong counter = new AtomicLong();

    /**
     * Initial default homepage for the API.
     * 
     * @param name
     * @return
     */
    @CrossOrigin
    @GetMapping("/")
    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
