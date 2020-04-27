package com.electiondataquality.features.state;

import java.util.HashSet;

import com.electiondataquality.features.Feature;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.Polygon;
import com.electiondataquality.jpa.objects.StateFeature;

public class State extends Feature {

    private int stateId;

    private String stateName;

    private String stateAbreviation;

    private HashSet<Integer> counties;

    private HashSet<Integer> districts;

    public State(StateFeature stateFeature) {
        this.stateId = stateFeature.getStateId();
        this.stateName = stateFeature.getStateName();
        this.stateAbreviation = stateFeature.getStateAbv();

        this.rawGeometry = stateFeature.getFeature().getGeometry();
        // TODO:
        // this.shape = stateFeature.getFeature().getGeometry()
    }

    public State(int stateId, String stateName, String stateAbreviation, HashSet<Integer> counties,
            HashSet<Integer> districts, Polygon shape) {
        super(shape);

        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbreviation = stateAbreviation;
        this.counties = counties;
        this.districts = districts;
    }

    public State(int stateId, String stateName, String stateAbreviation, HashSet<Integer> counties,
            HashSet<Integer> districts, MultiPolygon shape) {
        super(shape);

        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbreviation = stateAbreviation;
        this.counties = counties;
        this.districts = districts;
    }

    public int getId() {
        return this.stateId;
    }

    public String getName() {
        return this.stateName;
    }

    public String getAbreviation() {
        return this.stateAbreviation;
    }

    public HashSet<Integer> getCountiesId() {
        return this.counties;
    }

    public void addCounty(int newCountyId) {
        if (!this.counties.contains(newCountyId)) {
            this.counties.add(newCountyId);
        }
    }

    public void deleteCounty(int countyId) {
        if (this.counties.contains(countyId)) {
            this.counties.remove(countyId);
        }
    }

    public HashSet<Integer> getDistrictsId() {
        return this.districts;
    }

    public void addDistrict(int newDistrictId) {
        if (!this.districts.contains(newDistrictId)) {
            this.districts.add(newDistrictId);
        }
    }

    public void deleteDistrict(int districtId) {
        if (this.districts.contains(districtId)) {
            this.districts.remove(districtId);
        }
    }

    // TODO
    // DAVID_NOTE: Idk how to compute this because we need the managers to get all
    // precint's VotingData
    public VotingData computeVotingData() {
        return null;
    }

    // TODO
    // DAVID_NOTE: Idk how to compute this because we need the managers to get all
    // precint's DemographicData
    public DemographicData computeDemographicData() {
        return null;
    }

    @Override
    public String toString() {
        String str = this.stateName + "(" + this.stateAbreviation + ")\nID: " + Integer.toString(this.stateId) + "\n"
                + "Shape: " + this.shapeToString() + "\nRawGeo: " + this.rawGeometry + "\n";

        return str;
    }
}