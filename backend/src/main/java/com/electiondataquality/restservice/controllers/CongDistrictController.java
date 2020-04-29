package com.electiondataquality.restservice.controllers;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.types.responses.ApiResponse;
import com.electiondataquality.types.responses.ResponseGen;
import com.electiondataquality.types.responses.enums.API_STATUS;
import com.electiondataquality.features.congressional_district.CongressionalDistrict;
import com.electiondataquality.jpa.managers.CDEntityManager;
import com.electiondataquality.jpa.objects.CDFeature;

@RestController
@CrossOrigin
public class CongDistrictController {

    /**
     * Get the shape data for all of the states.
     * 
     * NOTE: Tested 4/29/2020
     * 
     * @param stateId
     * @return
     */
    @GetMapping("/congressionalDistrictsForState")
    public ApiResponse getCongDistrictForState(@RequestParam String stateId) {
        CDEntityManager cdem = new CDEntityManager(RestServiceApplication.emFactoryCDistrict);
        List<CDFeature> targetCDs = cdem.findAllCongressionalDistrictsByStateId(stateId);

        Map<String, CongressionalDistrict> cdMap = new HashMap<>();

        for (CDFeature cdFeature : targetCDs) {
            CongressionalDistrict cd = new CongressionalDistrict(cdFeature);

            cdMap.put(cd.getId(), cd);
        }

        cdem.cleanup();

        return ResponseGen.create(API_STATUS.OK, cdMap);
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
