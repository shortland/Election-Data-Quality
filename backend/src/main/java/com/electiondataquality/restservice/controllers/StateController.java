package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.StateManager;
import com.electiondataquality.restservice.features.state.State;

@RestController
public class StateController {

    /**
     * Get the shape data for all of the states
     */
    @GetMapping("/allStates")
    public ArrayList<State> getAllStates() {
        StateManager stateManager = RestServiceApplication.serverManager.getStateManager();

        return stateManager.getAllStates();
    }
}