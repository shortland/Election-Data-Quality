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
         * POPULATE TEST STATES
         */
        State a = new State(0, "New York", "NY", null, null);
        State b = new State(1, "Utah", "UT", null, null);
        State c = new State(2, "Wisconsin", "WI", null, null);

        HashSet<State> stateSet = new HashSet<State>();
        stateSet.add(a);
        stateSet.add(b);
        stateSet.add(c);

        RestServiceApplication.serverManager.getStateManager().populate(stateSet);

        /**
         * Run the rest API
         */
        SpringApplication.run(RestServiceApplication.class, args);
    }
}
