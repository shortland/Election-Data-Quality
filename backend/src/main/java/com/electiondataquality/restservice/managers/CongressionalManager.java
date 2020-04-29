package com.electiondataquality.restservice.managers;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;

import com.electiondataquality.features.congressional_district.CongressionalDistrict;

public class CongressionalManager {

    private Map<String, CongressionalDistrict> congressionalDistrictMap;

    public CongressionalManager() {
        this.congressionalDistrictMap = new HashMap<String, CongressionalDistrict>();
    }

    public CongressionalManager(Set<CongressionalDistrict> congDistrictSet) {
        this.congressionalDistrictMap = new HashMap<String, CongressionalDistrict>();

        for (CongressionalDistrict cd : congDistrictSet) {
            this.congressionalDistrictMap.put(cd.getId(), cd);
        }
    }

    // NOTE: this clears the map and populate the map with the congDistrictSet
    public void populate(Set<CongressionalDistrict> congDistrictSet) {
        this.congressionalDistrictMap.clear();

        for (CongressionalDistrict cd : congDistrictSet) {
            this.congressionalDistrictMap.put(cd.getId(), cd);
        }
    }

    public CongressionalDistrict getCongDistrict(String cdId) {
        if (this.congressionalDistrictMap.containsKey(cdId)) {
            return this.congressionalDistrictMap.get(cdId);
        }

        return null;
    }

    public Set<String> getAllChildrenPrecinct(String cdId) {
        if (this.congressionalDistrictMap.containsKey(cdId)) {
            return this.congressionalDistrictMap.get(cdId).getChildrenId();
        }

        return null;
    }

    public String toString() {
        String str = "";

        for (String id : this.congressionalDistrictMap.keySet()) {
            str = str + this.congressionalDistrictMap.get(id).toString() + "\n";
        }

        return str;
    }
}
