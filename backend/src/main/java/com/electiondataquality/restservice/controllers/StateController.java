package com.electiondataquality.restservice.controllers;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.electiondataquality.features.state.State;
import com.electiondataquality.jpa.objects.StateFeature;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.jpa.managers.StateEntityManager;
import com.electiondataquality.types.responses.enums.API_STATUS;
import com.electiondataquality.restservice.RestServiceApplication;

@CrossOrigin
@RestController
@EnableScheduling
public class StateController {

    /**
     * Revalidate cached datastore endpoints every 6 hours, Additionally, call this
     * method 1 second after running app for first time for initial cache.
     */
    @Scheduled(fixedRate = 6 * 60 * 60000)
    @Scheduled(initialDelay = 1000, fixedDelay = Long.MAX_VALUE)
    private void validateDataStoreCachedEndpoints() throws Exception {
        RestServiceApplication.logger
                .warn("Refreshing datastore cache for methods: '/allStates' @" + System.currentTimeMillis());

        // enable forcing
        RestServiceApplication.caching = false;

        this.getAllStates();

        // redisable forcing
        RestServiceApplication.caching = true;
    }

    /**
     * Get the shape data for all of the states
     * 
     * @throws Exception
     */
    @GetMapping("/allStates")
    public ApiResponse getAllStates() throws Exception {
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        RestServiceApplication.logger.info("Method:" + method);

        /**
         * Return the datastore cache when valid
         */
        if (RestServiceApplication.dataStore.isValid(method)) {
            RestServiceApplication.logger.info("Using datastore cache");

            return ResponseGen.create(API_STATUS.OK,
                    (new ObjectMapper()).readValue(RestServiceApplication.dataStore.readFile(method), Set.class));
        }

        StateEntityManager stateTableEm = new StateEntityManager(RestServiceApplication.emFactoryState,
                RestServiceApplication.emFactoryCounty, RestServiceApplication.emFactoryCDistrict);

        Set<State> stateSet = new HashSet<>();
        List<StateFeature> allStateFeatures = stateTableEm.findAllStateFeatures();

        for (StateFeature stateFeature : allStateFeatures) {
            State stateObj = new State(stateFeature, stateTableEm);

            stateSet.add(stateObj);
        }

        stateTableEm.cleanup();

        /**
         * Save the datastore cache
         */
        RestServiceApplication.dataStore.saveFile(method, stateSet);

        return ResponseGen.create(API_STATUS.OK, stateSet);
    }
}
