package com.electiondataquality.features.state;

import java.util.HashSet;

import com.electiondataquality.features.Feature;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.util.DataStructureUtil;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.util.RawGeometryToShape;
import com.electiondataquality.jpa.managers.StateEntityManager;
import com.electiondataquality.jpa.objects.StateFeature;
import com.electiondataquality.jpa.tables.CongressionalDistrictTable;
import com.electiondataquality.jpa.tables.CountyTable;

public class State extends Feature {

    private String stateId;

    private String stateName;

    private String stateAbreviation;

    private HashSet<CountyTable> counties;

    private HashSet<CongressionalDistrictTable> congressionalDistricts;

    public State(StateFeature stateFeature, StateEntityManager stateEm) {
        this.stateId = stateFeature.getStateId();
        this.stateName = stateFeature.getStateName();
        this.stateAbreviation = stateFeature.getStateAbv();
        this.geometry = RawGeometryToShape.convertRawToGeometry(stateFeature.getFeature().getGeometry());
        this.counties = DataStructureUtil.listToHashSet(stateEm.findAllCountiesByStateId(stateFeature.getStateId()));
        this.congressionalDistricts = DataStructureUtil
                .listToHashSet(stateEm.findAllCongressionalDistrictsByStateId(stateFeature.getStateId()));
    }

    public State(String stateId, String stateName, String stateAbreviation, HashSet<CountyTable> counties,
            HashSet<CongressionalDistrictTable> congrDistricts, MultiPolygon shape) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbreviation = stateAbreviation;
        this.counties = counties;
        this.congressionalDistricts = congrDistricts;
    }

    public String getId() {
        return this.stateId;
    }

    public String getName() {
        return this.stateName;
    }

    public String getAbreviation() {
        return this.stateAbreviation;
    }

    public HashSet<String> getCountyIds() {
        HashSet<String> countyIds = new HashSet<>();

        for (CountyTable county : counties) {
            countyIds.add(county.getFipsCode());
        }

        return countyIds;
    }

    public HashSet<String> getCongressionalDistrictIds() {
        HashSet<String> congrIds = new HashSet<>();

        for (CongressionalDistrictTable congrD : congressionalDistricts) {
            congrIds.add(congrD.getCdId() + "");
        }

        return congrIds;
    }

    // public void addCounty(CountyTable countyNew) {
    // boolean found = false;

    // for (CountyTable county : counties) {
    // if (county.getFipsCode() == countyNew.getFipsCode()) {
    // found = true;
    // }
    // }

    // if (found == false) {
    // counties.add(countyNew);
    // }
    // }

    // public void deleteCountyById(String countyId) {
    // for (CountyTable county : counties) {
    // if (county.getFipsCode() == countyId) {
    // counties.remove(county);
    // }
    // }
    // }

    // public void addCongrD(CongressionalDistrictTable congrDistNew) {
    // boolean found = false;

    // for (CongressionalDistrictTable congrD : congressionalDistricts) {
    // if (congrD.getCdId() == congrDistNew.getCdId()) {
    // found = true;
    // }
    // }

    // if (found == false) {
    // congressionalDistricts.add(congrDistNew);
    // }
    // }

    // public void deleteDistrictId(String districtId) {
    // if (this.congressionalDistricts.contains(districtId)) {
    // this.congressionalDistricts.remove(districtId);
    // }
    // }

    /**
     * TODO:
     * 
     * DAVID NOTE: Idk how to compute this because we need the managers to get all
     * precint's VotingData.
     */
    public VotingData computeVotingData() {
        return null;
    }

    /**
     * TODO:
     * 
     * DAVID NOTE: Idk how to compute this because we need the managers to get all
     * precint's DemographicData.
     */
    public DemographicData computeDemographicData() {
        return null;
    }

    @Override
    public String toString() {
        return this.stateName + "(" + this.stateAbreviation + ")\nID: " + this.stateId + "\n" + "Shape: "
                + this.shapeToString() + "\nGeometry: " + "TODO" + "\n";
    }
}
