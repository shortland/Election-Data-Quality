package com.electiondataquality.restservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.electiondataquality.restservice.filesaver.FileSaver;
import com.electiondataquality.restservice.managers.ServerManager;

@SpringBootApplication
public class RestServiceApplication implements CommandLineRunner {

    public static ServerManager serverManager;

    public static Logger logger;

    public static EntityManagerFactory emFactoryState;

    public static EntityManagerFactory emFactoryCounty;

    public static EntityManagerFactory emFactoryCDistrict;

    public static EntityManagerFactory emFactoryPrecinct;

    public static FileSaver dataStore;

    public static boolean caching;

    public static void main(String[] args) {

        /**
         * Create a server manager object. It's static - thus it's shared amongest the
         * whole application.
         */
        RestServiceApplication.serverManager = new ServerManager();

        /**
         * Initialize the logger
         */
        RestServiceApplication.logger = LogManager.getLogger("ElectionDataQuality");

        /**
         * Run the rest API
         */
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {

        /**
         * Enable caching
         */
        RestServiceApplication.caching = true;

        /**
         * Initialize the data store; 2 minute valid data time.
         */
        try {
            // Every 12 hours revalidate data
            RestServiceApplication.dataStore = new FileSaver("datastore", 12 * 60 * 60000);
        } catch (IOException e) {
            RestServiceApplication.logger.warn("Failed to create FileSaver datastore " + e.getStackTrace());

            System.exit(1);
        }

        /**
         * Setup Entity Manager Factories
         */

        emFactoryState = Persistence.createEntityManagerFactory("StateTable");

        emFactoryCounty = Persistence.createEntityManagerFactory("CountyTable");

        emFactoryCDistrict = Persistence.createEntityManagerFactory("CongressionalDistrictTable");

        emFactoryPrecinct = Persistence.createEntityManagerFactory("PrecinctTable");

        /** App Live */
        RestServiceApplication.logger.info("Application has started");
    }
}
