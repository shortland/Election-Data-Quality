package com.electiondataquality.features.state;

import java.util.HashSet;

import com.electiondataquality.features.Feature;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.Polygon;
import com.electiondataquality.geometry.util.RawGeometryToShape;
import com.electiondataquality.jpa.objects.StateFeature;

public class State extends Feature {

    private String stateId;

    private String stateName;

    private String stateAbreviation;

    private HashSet<String> counties;

    private HashSet<String> districts;

    public State(StateFeature stateFeature) {
        this.stateId = stateFeature.getStateId();
        this.stateName = stateFeature.getStateName();
        this.stateAbreviation = stateFeature.getStateAbv();
        this.geometry = RawGeometryToShape.convertRawToGeometry(stateFeature.getFeature().getGeometry());
    }

    public State(String stateId, String stateName, String stateAbreviation, HashSet<String> counties,
            HashSet<String> districts, Polygon shape) {
        super(shape);

        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbreviation = stateAbreviation;
        this.counties = counties;
        this.districts = districts;
    }

    public State(String stateId, String stateName, String stateAbreviation, HashSet<String> counties,
            HashSet<String> districts, MultiPolygon shape) {
        super(shape);

        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbreviation = stateAbreviation;
        this.counties = counties;
        this.districts = districts;
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

    public HashSet<String> getCountiesId() {
        return this.counties;
    }

    public void addCounty(String newCountyId) {
        if (!this.counties.contains(newCountyId)) {
            this.counties.add(newCountyId);
        }
    }

    public void deleteCounty(String countyId) {
        if (this.counties.contains(countyId)) {
            this.counties.remove(countyId);
        }
    }

    public HashSet<String> getDistrictsId() {
        return this.districts;
    }

    public void addDistrict(String newDistrictId) {
        if (!this.districts.contains(newDistrictId)) {
            this.districts.add(newDistrictId);
        }
    }

    public void deleteDistrict(String districtId) {
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
        return this.stateName + "(" + this.stateAbreviation + ")\nID: " + this.stateId + "\n" + "Shape: "
                + this.shapeToString() + "\nGeometry: " + "TODO" + "\n";
    }
}
