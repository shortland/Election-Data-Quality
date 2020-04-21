package com.electiondataquality.restservice.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.electiondataquality.restservice.RestServiceApplication;
import com.electiondataquality.restservice.managers.StateManager;
import com.electiondataquality.restservice.managers.CongressionalManager;
import com.electiondataquality.features.congressional_district.CongressionalDistrict;

@RestController
public class CongDistrictController {

    /**
     * Get the shape data for all of the states
     */
    @GetMapping("/congressionalDistrictsForState")
    public ArrayList<CongressionalDistrict> getCongDistrictForState(@RequestParam int stateId) {
        StateManager stateManager = RestServiceApplication.serverManager.getStateManager();
        CongressionalManager cdManager = RestServiceApplication.serverManager.getCongressionalManager();
        HashSet<Integer> congDistrictIds = stateManager.getAllDistricts(stateId);
        ArrayList<CongressionalDistrict> cdList = new ArrayList<>();

        if (congDistrictIds != null) {
            for (Integer cdId : congDistrictIds) {
                CongressionalDistrict cd = cdManager.getCongDistrict(cdId.intValue());

                if (cd != null) {
                    cdList.add(cd);
                }
            }
        }

        return cdList;
    }

}
