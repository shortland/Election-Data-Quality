package com.electiondataquality.restservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electiondataquality.restservice.managers.ServerManager;

@SpringBootApplication
public class RestServiceApplication implements CommandLineRunner {

    public static ServerManager serverManager;

    public static void main(String[] args) {

        /**
         * Create a server manager object. It's static - thus it's shared amongest the
         * whole application.
         */
        RestServiceApplication.serverManager = new ServerManager();

        /**
         * Run the rest API
         */
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("Application is now live.");
    }
}
