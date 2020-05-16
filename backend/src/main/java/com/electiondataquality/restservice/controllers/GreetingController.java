package com.electiondataquality.restservice.controllers;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.index.Greeting;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.enums.API_STATUS;

@RestController
@CrossOrigin
public class GreetingController {

    private static final String template = "Hello, %s! Please specify an endpoint.";

    private final AtomicLong counter = new AtomicLong();

    /**
     * Initial default homepage for the API.
     * 
     * @param name
     * @return
     */
    @GetMapping("/")
    public ApiResponse greeting(@RequestParam(defaultValue = "World") String name) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

        return ResponseGen.create(API_STATUS.OK,
                new Greeting(counter.incrementAndGet(), String.format(template, name)));
    }
}
