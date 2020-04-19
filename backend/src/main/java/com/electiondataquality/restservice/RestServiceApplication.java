package com.electiondataquality.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electiondataquality.restservice.managers.ServerManager;

@SpringBootApplication
public class RestServiceApplication {

    /**
     * Create the ServerManager object
     */
    public static ServerManager serverManager;

    public static void main(String[] args) {
        RestServiceApplication.serverManager = new ServerManager();

        /**
         * Run the rest API
         */
        SpringApplication.run(RestServiceApplication.class, args);
    }
}
