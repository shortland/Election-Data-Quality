package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.StateManager;
import com.electiondataquality.restservice.managers.CongressionalManager;
import com.electiondataquality.features.congressional_district.CongressionalDistrict;

@RestController
@CrossOrigin
public class CongDistrictController {

    /**
     * Get the shape data for all of the states.
     * 
     * @param stateId
     * @return
     */
    @GetMapping("/congressionalDistrictsForState")
    public ArrayList<CongressionalDistrict> getCongDistrictForState(@RequestParam String stateId) {
        StateManager stateManager = RestServiceApplication.serverManager.getStateManager();
        CongressionalManager cdManager = RestServiceApplication.serverManager.getCongressionalManager();

        HashSet<String> congDistrictIds = stateManager.getAllDistricts(stateId);
        ArrayList<CongressionalDistrict> cdList = new ArrayList<>();

        if (congDistrictIds != null) {
            for (String cdId : congDistrictIds) {
                CongressionalDistrict cd = cdManager.getCongDistrict(cdId);

                if (cd != null) {
                    cdList.add(cd);
                }
            }
        }

        return cdList;
    }

    /**
     * NOTE: For testing
     * 
     * @param cid
     * @return
     */
    @GetMapping("/congressionalDistrict")
    public CongressionalDistrict x(@RequestParam String cid) {
        CongressionalManager cdManager = RestServiceApplication.serverManager.getCongressionalManager();

        return cdManager.getCongDistrict(cid);
    }
}
