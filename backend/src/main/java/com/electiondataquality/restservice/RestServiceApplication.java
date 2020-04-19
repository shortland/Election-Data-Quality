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
        /**
         * Create a server manager object. It's static - thus it's shared amongest the
         * whole application.
         */
        RestServiceApplication.serverManager = new ServerManager();

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
        DataPopulator populator = new DataPopulator(RestServiceApplication.serverManager);
        populator.populateStates();

        /**
         * Run the rest API
         */
        SpringApplication.run(RestServiceApplication.class, args);
    }
}
