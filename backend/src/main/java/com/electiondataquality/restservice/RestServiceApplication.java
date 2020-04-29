package com.electiondataquality.restservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.electiondataquality.restservice.managers.ServerManager;

@SpringBootApplication
public class RestServiceApplication implements CommandLineRunner {

    public static ServerManager serverManager;

    public static EntityManagerFactory emFactoryState;

    public static EntityManagerFactory emFactoryCounty;

    public static EntityManagerFactory emFactoryCDistrict;

    public static EntityManagerFactory emFactoryPrecinct;

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
         * Setup Entity Manager Factories
         */

        emFactoryState = Persistence.createEntityManagerFactory("StateTable");

        emFactoryCounty = Persistence.createEntityManagerFactory("CountyTable");

        emFactoryCDistrict = Persistence.createEntityManagerFactory("CongressionalDistrictTable");

        emFactoryPrecinct = Persistence.createEntityManagerFactory("PrecinctTable");

        /** App Live */
        System.out.println("Application is now live.");
    }
}
