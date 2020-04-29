package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
public class CongDistrictController {

    /**
     * Get the shape data for all of the states.
     * 
     * @param stateId
     * @return
     */
    @CrossOrigin
    @GetMapping("/congressionalDistrictsForState")
    public ArrayList<CongressionalDistrict> getCongDistrictForState(@RequestParam String stateId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("CongressionalTable");
        CDEntityManager cdem = new CDEntityManager(emf);
        List<CDFeature> targetCDs = cdem.findAllCongressionalDistrictsByStateId(stateId);
        ArrayList<CongressionalDistrict> cdList = new ArrayList<>();
        for (CDFeature cdFeature : targetCDs) {
            CongressionalDistrict cd = new CongressionalDistrict(cdFeature);
            cdList.add(cd);
        }
        cdem.cleanup();

        return cdList;
    }

    /**
     * NOTE: For testing
     * 
     * @param cid
     * @return
     */
    @CrossOrigin
    @GetMapping("/congressionalDistrict")
    public CongressionalDistrict x(@RequestParam String cid) {
        CongressionalManager cdManager = RestServiceApplication.serverManager.getCongressionalManager();
        CongressionalDistrict c = cdManager.getCongDistrict(cid);

        return c;
    }
}
