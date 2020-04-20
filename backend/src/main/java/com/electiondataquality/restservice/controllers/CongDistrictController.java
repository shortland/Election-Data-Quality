package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.StateManager;
import com.electiondataquality.restservice.managers.CongressionalManager;
import com.electiondataquality.restservice.features.state.State;
import com.electiondataquality.restservice.features.congressional_district.CongressionalDistrict;
import com.electiondataquality.restservice.features.county.County;

@RestController
public class CongDistrictController {

    /**
     * Get the shape data for all of the states
     */
    @GetMapping("/congressionalDistrictsForState")
    public ArrayList<CongressionalDistrict> getCongDistrictForState(@RequestParam(value = "stateId") int stateId) {
        StateManager stateManager = RestServiceApplication.serverManager.getStateManager();
        CongressionalManager cdManager = RestServiceApplication.serverManager.getCongressionalManager();
        HashSet<Integer> congDistrictIds = stateManager.getAllDistricts(stateId);
        ArrayList<CongressionalDistrict> cdList = new ArrayList<CongressionalDistrict>();
        for (int cdId : congDistrictIds) {
            CongressionalDistrict cd = cdManager.getCongDistrict(cdId);
            if (cd != null) {
                cdList.add(cd);
            }
        }

        return cdList;
    }
}
