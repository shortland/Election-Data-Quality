package com.electiondataquality.restservice.controllers;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.electiondataquality.features.Feature;
import com.electiondataquality.jpa.objects.CDFeature;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.jpa.managers.CDEntityManager;
import com.electiondataquality.types.responses.enums.API_STATUS;
import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.features.util.FeatureCollectionWrapper;
import com.electiondataquality.features.congressional_district.CongressionalDistrict;

@CrossOrigin
@RestController
@EnableScheduling
public class CongDistrictController {

    /**
     * Revalidate cached datastore endpoints every 6 hours, Additionally, call this
     * method 2 seconds after running app for first time for initial cache.
     */
    @Scheduled(fixedRate = 6 * 60 * 60000)
    @Scheduled(initialDelay = 6000, fixedDelay = Long.MAX_VALUE)
    private void validateDataStoreCachedEndpoints() throws Exception {
        RestServiceApplication.logger
                .warn("Refreshing datastore cache for methods: '/allStates' @" + System.currentTimeMillis());

        // enable forcing
        RestServiceApplication.caching = false;

        this.getAllCongressionalDistricts();

        // redisable forcing
        RestServiceApplication.caching = true;
    }

    /**
     * Get the shape data for all of the congressional districts.
     * 
     * NOTE: Tested 5/5/2020
     * 
     * @param stateId
     * @return
     * @throws IOException
     */
    @GetMapping("/allCongressionalDistricts")
    public ApiResponse getAllCongressionalDistricts() throws Exception {
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        RestServiceApplication.logger.info("Method:" + method);

        /**
         * Return the datastore cache when valid
         */
        if (RestServiceApplication.dataStore.isValid(method)) {
            RestServiceApplication.logger.info("Using datastore cache");

            return ResponseGen.create(API_STATUS.OK, (new ObjectMapper())
                    .readValue(RestServiceApplication.dataStore.readFile(method), FeatureCollectionWrapper.class));
        }

        CDEntityManager cdem = new CDEntityManager(RestServiceApplication.emFactoryCDistrict);
        List<CDFeature> targetCDs = cdem.findAllCDFeature();

        List<Feature> congDistricts = new ArrayList<>();

        for (CDFeature cdFeature : targetCDs) {
            CongressionalDistrict cd = new CongressionalDistrict(cdFeature);

            congDistricts.add(cd);
        }

        cdem.cleanup();

        FeatureCollectionWrapper wrapper = new FeatureCollectionWrapper();
        wrapper.setFeatures(congDistricts);

        /**
         * Save the datastore cache
         */
        RestServiceApplication.dataStore.saveFile(method, wrapper);

        return ResponseGen.create(API_STATUS.OK, wrapper);
    }

    /**
     * Get the shape data for all of the congressional districts of a state.
     * 
     * NOTE: Tested 5/5/2020
     * 
     * @param stateId
     * @return
     */
    @GetMapping("/congressionalDistrictsForState")
    public ApiResponse getCongDistrictForState(@RequestParam String stateId) throws Exception {
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        RestServiceApplication.logger.info("Method:" + method);

        /**
         * Return the datastore cache when valid
         */
        if (RestServiceApplication.dataStore.isValid(method + "_" + stateId)) {
            RestServiceApplication.logger.info("Using datastore cache");

            return ResponseGen.create(API_STATUS.OK, (new ObjectMapper()).readValue(
                    RestServiceApplication.dataStore.readFile(method + "_" + stateId), FeatureCollectionWrapper.class));
        }

        CDEntityManager cdem = new CDEntityManager(RestServiceApplication.emFactoryCDistrict);
        List<CDFeature> targetCDs = cdem.findAllCongressionalDistrictsByStateId(stateId);

        List<Feature> congDistricts = new ArrayList<>();

        for (CDFeature cdFeature : targetCDs) {
            CongressionalDistrict cd = new CongressionalDistrict(cdFeature);

            congDistricts.add(cd);
        }

        cdem.cleanup();

        FeatureCollectionWrapper wrapper = new FeatureCollectionWrapper();
        wrapper.setFeatures(congDistricts);

        /**
         * Save the datastore cache
         */
        RestServiceApplication.dataStore.saveFile(method + "_" + stateId, wrapper);

        return ResponseGen.create(API_STATUS.OK, wrapper);
    }

    /**
     * NOTE: For testing
     * 
     * @param cid
     * @return
     */
    // @GetMapping("/congressionalDistrict")
    // public CongressionalDistrict x(@RequestParam String cid) {
    // CDEntityManager cdem = new
    // CDEntityManager(RestServiceApplication.emFactoryCDistrict);
    // Optional<CDFeature> x = cdem.findCDFeatureById(cid);
    // if (x.isPresent()) {
    // CongressionalDistrict cd = new CongressionalDistrict(x.get());
    // cdem.cleanup();
    // return cd;
    // }
    // cdem.cleanup();
    // return null;
    // }
}
