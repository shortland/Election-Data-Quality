package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.electiondataquality.features.county.County;
import com.electiondataquality.jpa.managers.CountyEntityManager;
import com.electiondataquality.jpa.tables.CountyTable;
import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.enums.API_STATUS;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CountyController {

    /**
     * NOTE: There is no automatic caching in this controller
     * 
     * @throws Exception
     */

    @GetMapping("/countiesInState")
    public ApiResponse getCountyByState(@RequestParam String stateId) throws Exception {
        String method = Thread.currentThread().getStackTrace()[1].getMethodName();
        RestServiceApplication.logger.info("Method:" + method);

        /**
         * Return the datastore cache when valid
         */
        if (RestServiceApplication.dataStore.isValid(method + "_" + stateId) && RestServiceApplication.caching) {
            RestServiceApplication.logger.info("Using datastore cache");

            return ResponseGen.create(API_STATUS.OK, (new ObjectMapper())
                    .readValue(RestServiceApplication.dataStore.readFile(method + "_" + stateId), List.class));
        }

        CountyEntityManager cem = new CountyEntityManager(RestServiceApplication.emFactoryCounty);
        List<CountyTable> targetCounties = cem.findCountiesByState(stateId);

        List<Map<String, Object>> counties = new ArrayList<>();

        for (CountyTable county : targetCounties) {
            Map<String, Object> c = new HashMap<>();
            County countyForReturn = new County(county);

            c.put("countyId", countyForReturn.getId());
            c.put("name", countyForReturn.getName());
            c.put("geometry", countyForReturn.geometry);

            counties.add(c);
        }

        cem.cleanup();

        /**
         * Save the datastore cache
         */
        RestServiceApplication.dataStore.saveFile(method + "_" + stateId, counties);

        return ResponseGen.create(API_STATUS.OK, counties);
    }

}