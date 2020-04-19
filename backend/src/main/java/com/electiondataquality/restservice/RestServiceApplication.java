package com.electiondataquality.restservice;

import java.util.HashSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electiondataquality.restservice.managers.ServerManager;
import com.electiondataquality.restservice.features.state.State;

@SpringBootApplication
public class RestServiceApplication {

    /**
     * Create the ServerManager object
     */
    public static ServerManager serverManager;

    public static void main(String[] args) {
        RestServiceApplication.serverManager = new ServerManager();

        /**
         * Populate dummy datas
         */
        RestServiceApplication.populateDummyData();

        /**
         * Run the rest API
         */
        SpringApplication.run(RestServiceApplication.class, args);
    }

    private static void populateDummyData() {
        /**
         * POPULATE STATES
         */
        State a = new State(36, "New York", "NY", null, null);
        State b = new State(49, "Utah", "UT", null, null);
        State c = new State(55, "Wisconsin", "WI", null, null);

        HashSet<State> stateSet = new HashSet<State>();
        stateSet.add(a);
        stateSet.add(b);
        stateSet.add(c);

        RestServiceApplication.serverManager.getStateManager().populate(stateSet);

        /**
         * POPULATE CONGRESSIONAL DISTRICTS FOR UTAH
         */

    }
}
