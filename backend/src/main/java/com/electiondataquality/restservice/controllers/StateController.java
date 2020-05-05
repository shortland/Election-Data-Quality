package com.electiondataquality.restservice.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electiondataquality.features.state.State;
import com.electiondataquality.jpa.managers.StateEntityManager;
import com.electiondataquality.jpa.objects.StateFeature;
import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.enums.API_STATUS;

@RestController
@CrossOrigin
public class StateController {

    /**
     * Get the shape data for all of the states
     */
    @GetMapping("/allStates")
    public ApiResponse getAllStates() {
        StateEntityManager stateTableEm = new StateEntityManager(RestServiceApplication.emFactoryState,
                RestServiceApplication.emFactoryCounty, RestServiceApplication.emFactoryCDistrict);

        Set<State> stateSet = new HashSet<>();
        List<StateFeature> allStateFeatures = stateTableEm.findAllStateFeatures();

        for (StateFeature stateFeature : allStateFeatures) {
            State stateObj = new State(stateFeature, stateTableEm);

            stateSet.add(stateObj);
        }

        stateTableEm.cleanup();

        return ResponseGen.create(API_STATUS.OK, stateSet);
    }
}
