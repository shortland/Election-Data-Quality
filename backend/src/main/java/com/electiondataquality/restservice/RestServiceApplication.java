package com.electiondataquality.restservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

import com.electiondataquality.restservice.config.DatabaseConfig;
import com.electiondataquality.restservice.managers.ServerManager;

@SpringBootApplication
public class RestServiceApplication implements CommandLineRunner {

    @Autowired
    private DatabaseConfig databaseConfig;

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

        /**
         * TODO:
         * 
         * Populate server memory with data hard-coded & locally stored (file: GeoJSON)
         * data. Eventually this should be removed in favor of a database & JPA
         * (persistence layer).
         * 
         * After DB is implemented - keep the DataPopular class around though (commented
         * out or whatever) - it could make testing in dev environment easier in some
         * occasions.
         */
        DataPopulator populator = new DataPopulator(RestServiceApplication.serverManager, databaseConfig);
        populator.populateStates();
        populator.populateCongressional();
        populator.populatePrecinctAndComments();
    }
}
