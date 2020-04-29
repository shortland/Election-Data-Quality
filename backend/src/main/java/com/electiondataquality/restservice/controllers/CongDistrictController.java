package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.StateManager;
import com.electiondataquality.restservice.managers.CongressionalManager;
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
    public HashMap<String, CongressionalDistrict> getCongDistrictForState(@RequestParam String stateId) {
        CDEntityManager cdem = new CDEntityManager(RestServiceApplication.emFactoryCDistrict);
        List<CDFeature> targetCDs = cdem.findAllCongressionalDistrictsByStateId(stateId);
        // ArrayList<CongressionalDistrict> cdList = new ArrayList<>();
        HashMap<String, CongressionalDistrict> cdMap = new HashMap<>();
        for (CDFeature cdFeature : targetCDs) {
            CongressionalDistrict cd = new CongressionalDistrict(cdFeature);
            cdMap.put(cd.getId(), cd);
        }
        cdem.cleanup();

        return cdMap;
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
