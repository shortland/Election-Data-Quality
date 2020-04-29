package com.electiondataquality.restservice.controllers;

import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electiondataquality.features.state.State;
import com.electiondataquality.jpa.managers.StateEntityManager;
import com.electiondataquality.jpa.objects.StateFeature;

@RestController
public class StateController {

    /**
     * Get the shape data for all of the states
     */
    @CrossOrigin
    @GetMapping("/allStates")
    public HashSet<State> getAllStates() {
        EntityManagerFactory emFactoryState = Persistence.createEntityManagerFactory("StateTable");
        EntityManagerFactory emFactoryCounty = Persistence.createEntityManagerFactory("CountyTable");
        EntityManagerFactory emFactoryCDistrict = Persistence.createEntityManagerFactory("CongressionalDistrictTable");
        StateEntityManager stateTableEm = new StateEntityManager(emFactoryState, emFactoryCounty, emFactoryCDistrict);

        HashSet<State> stateSet = new HashSet<>();
        List<StateFeature> allStateFeatures = stateTableEm.findAllStateFeatures();

        for (StateFeature stateFeature : allStateFeatures) {
            State stateObj = new State(stateFeature, stateTableEm);
            stateSet.add(stateObj);
        }

        stateTableEm.cleanup(true);

        return stateSet;
    }
}
