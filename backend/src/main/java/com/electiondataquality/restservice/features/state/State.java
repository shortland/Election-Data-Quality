package com.electiondataquality.restservice.features.state;

import java.util.ArrayList;
import java.util.HashSet;

import com.electiondataquality.restservice.features.Feature;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.voting.VotingData;

public class State extends Feature {
    private int stateId;
    private String stateName;
    private String stateAbreviation;
    private HashSet<Integer> counties;
    private HashSet<Integer> districts;

    public State(int stateId, String stateName, String stateAbreviation, HashSet<Integer> counties,
            HashSet<Integer> districts, ArrayList<ArrayList<double[]>> shape) {
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

    public String toString() {
        String str = this.stateName + "(" + this.stateAbreviation + ")\nID : " + Integer.toString(this.stateId) + "\n";
        str = str + "Counties : " + this.counties.toString() + "\nDistricts : " + this.districts.toString();
        return str;
    }
}