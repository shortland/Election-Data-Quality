package com.electiondataquality.restservice.controllers;

import java.util.HashSet;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electiondataquality.features.state.State;
import com.electiondataquality.jpa.managers.StateEntityManager;
import com.electiondataquality.jpa.objects.StateFeature;
import com.electiondataquality.restservice.RestServiceApplication;

@RestController
@CrossOrigin
public class StateController {

    /**
     * Get the shape data for all of the states
     */
    @GetMapping("/allStates")
    public HashSet<State> getAllStates() {
        StateEntityManager stateTableEm = new StateEntityManager(RestServiceApplication.emFactoryState,
                RestServiceApplication.emFactoryCounty, RestServiceApplication.emFactoryCDistrict);

        HashSet<State> stateSet = new HashSet<>();
        List<StateFeature> allStateFeatures = stateTableEm.findAllStateFeatures();

        for (StateFeature stateFeature : allStateFeatures) {
            State stateObj = new State(stateFeature, stateTableEm);
            stateSet.add(stateObj);
        }

        return stateSet;
    }
}
