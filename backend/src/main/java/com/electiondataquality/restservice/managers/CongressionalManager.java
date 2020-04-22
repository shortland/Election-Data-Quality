package com.electiondataquality.restservice.managers;

import java.util.HashSet;
import java.util.HashMap;

import com.electiondataquality.features.congressional_district.CongressionalDistrict;

public class CongressionalManager {
    private HashMap<Integer, CongressionalDistrict> congressionalDistrictMap;

    public CongressionalManager() {
        this.congressionalDistrictMap = new HashMap<Integer, CongressionalDistrict>();
    }

    public CongressionalManager(HashSet<CongressionalDistrict> congDistrictSet) {
        this.congressionalDistrictMap = new HashMap<Integer, CongressionalDistrict>();

        for (CongressionalDistrict cd : congDistrictSet) {
            this.congressionalDistrictMap.put(cd.getId(), cd);
        }
    }

    // NOTE: this clears the map and populate the map with the congDistrictSet
    public void populate(HashSet<CongressionalDistrict> congDistrictSet) {
        this.congressionalDistrictMap.clear();

        for (CongressionalDistrict cd : congDistrictSet) {
            this.congressionalDistrictMap.put(cd.getId(), cd);
        }
    }

    public CongressionalDistrict getCongDistrict(int cdId) {
        if (this.congressionalDistrictMap.containsKey(cdId)) {
            return this.congressionalDistrictMap.get(cdId);
        }

        return null;
    }

    public HashSet<Integer> getAllChildrenPrecinct(int cdId) {
        if (this.congressionalDistrictMap.containsKey(cdId)) {
            return this.congressionalDistrictMap.get(cdId).getChildrenId();
        }

        return null;
    }

    public String toString() {
        String str = "";

        for (int id : this.congressionalDistrictMap.keySet()) {
            str = str + this.congressionalDistrictMap.get(id).toString() + "\n";
        }

        return str;
    }
}
