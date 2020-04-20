package com.electiondataquality.restservice.features.precinct;

import java.util.HashMap;
import java.util.HashSet;

import com.electiondataquality.restservice.features.Feature;
import com.electiondataquality.restservice.features.precinct.error.PrecinctError;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.restservice.geometry.MultiPolygon;

// @Entity
// @Table(name = "PRECINTS")
public class Precinct extends Feature {
    private int id;
    private String canonicalName;
    private String fullName;
    private int parentDistrictId;
    private VotingData votingData;
    private DemographicData demographicData;
    private HashSet<Integer> neighborsId;
    private HashMap<Integer, PrecinctError> precinctErrors;
    private boolean isGhost;

    public Precinct(int id, String canonicalName, String fullName, int parentDistrictId, VotingData votingData,
            DemographicData demographicData, HashSet<Integer> neighborsId, HashSet<PrecinctError> errors,
            MultiPolygon multiPolygon) {
        super(multiPolygon);

        this.id = id;
        this.canonicalName = canonicalName;
        this.fullName = fullName;
        this.parentDistrictId = parentDistrictId;
        this.votingData = votingData;
        this.demographicData = demographicData;
        this.isGhost = false;
        this.neighborsId = neighborsId;
        if (errors != null) {
            this.precinctErrors = this.populateErrorsMap(errors);
        }
    }

    private HashMap<Integer, PrecinctError> populateErrorsMap(HashSet<PrecinctError> errors) {
        HashMap<Integer, PrecinctError> errorMap = new HashMap<Integer, PrecinctError>();

        for (PrecinctError e : errors) {
            errorMap.put(e.getId(), e);
        }

        return errorMap;
    }

    public int getId() {
        return this.id;
    }

    public int getParentDistrictId() {
        return this.parentDistrictId;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public HashSet<Integer> getNeighborsId() {
        return this.neighborsId;
    }

    public boolean addNeighbor(int neighborId) {
        return this.neighborsId.add(neighborId);
    }

    public boolean deleteNeighbor(int neighborId) {
        return this.neighborsId.remove(neighborId);
    }

    public VotingData getVotingData() {
        return this.votingData;
    }

    public void setVotingData(VotingData votingData) {
        this.votingData = votingData;
    }

    // public DemographicData getDemographicData() {
    // return this.demographicData;
    // }

    public void setDemographicData(DemographicData demographicData) {
        this.demographicData = demographicData;
    }

    public boolean getIsGhost() {
        return this.isGhost;
    }

    public void setGhost(boolean isGhost) {
        this.isGhost = isGhost;
    }

    // public HashSet<PrecinctError> getAllError() {
    // HashSet<PrecinctError> errorSet = new HashSet<PrecinctError>();
    // for (Integer id : precinctErrors.keySet()) {
    // errorSet.add(precinctErrors.get(id));
    // }
    // return errorSet;
    // }

    public void addError(PrecinctError error) {
        int eId = error.getId();

        if (this.precinctErrors.containsKey(eId)) {
            this.precinctErrors.remove(eId);
        }

        this.precinctErrors.put(eId, error);
    }

    public void deleteError(int errorId) {
        if (this.precinctErrors.containsKey(errorId)) {
            this.precinctErrors.remove(errorId);
        }
    }

    public String toString() {
        String str = "";
        str = str + "PrecinctId : " + this.getId() + "\n";
        str = str + "Name : " + this.getFullName() + " (" + this.getCanonicalName() + ")\n";
        str = str + "ParentId : " + this.getParentDistrictId() + "\n";
        str = str + "NeighborsId : " + this.getNeighborsId().toString() + "\n";
        str = str + this.votingData.toString();
        str = str + this.demographicData.toString();

        for (Integer eId : this.precinctErrors.keySet()) {
            str = str + this.precinctErrors.get(eId).toString() + "\n";
        }

        return str;
    }
}
