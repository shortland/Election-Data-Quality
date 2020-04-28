package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.StateManager;
import com.electiondataquality.features.state.State;

@RestController
public class StateController {

    /**
     * Get the shape data for all of the states
     */
<<<<<<< HEAD
    @CrossOrigin(origins = "http://localhost:3000")
=======
    @CrossOrigin
>>>>>>> 9067aba0f4e9cb5b9c6672cdacebbffe0d42120c
    @GetMapping("/allStates")
    public ArrayList<State> getAllStates() {
        StateManager stateManager = RestServiceApplication.serverManager.getStateManager();

        return stateManager.getAllStates();
    }
}
