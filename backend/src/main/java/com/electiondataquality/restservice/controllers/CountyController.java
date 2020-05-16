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

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CountyController {

    @GetMapping("/countiesInState")
    public ApiResponse getCountyByState(@RequestParam String stateId) {
        RestServiceApplication.logger.info("Method:" + Thread.currentThread().getStackTrace()[1].getMethodName());

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

        RestServiceApplication.logger.debug("counties in state:", counties);

        return ResponseGen.create(API_STATUS.OK, counties);
    }

}