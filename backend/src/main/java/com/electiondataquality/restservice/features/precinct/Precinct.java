package com.electiondataquality.restservice.features.precinct;

import java.util.HashMap;
import java.util.HashSet;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    // public static Precinct mergePrecinct(Precinct p1, Precinct p2) {
    // int id = p1.getId();
    // String cName = p1.getCanonicalName();
    // String fullName = p1.getFullName();
    // int parentId = p1.getParentDistrictId();
    // HashSet<Integer> neighborsId = p1.getNeighborsId();
    // VotingData vd = p1.getVotingData();
    // DemographicData dd = p1.getDemographicData();
    // HashMap<Integer, PrecinctError> errors = p1.getPrecinctErrors();
    // Precinct result = new Precinct();
    // return result;
    // }

    public static Precinct copyPrecinct(Precinct p) {
        Precinct ans = new Precinct(p.getId(), p.getCanonicalName(), p.getFullName(), p.getParentDistrictId(),
                p.getVotingData(), p.getDemographicData(), p.getNeighborsId(), p.getAllError(), p.getMultiPolygon());
        return ans;
    }

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

    // for update info
    public void setId(int id) {
        this.id = id;
    }

    public int getParentDistrictId() {
        return this.parentDistrictId;
    }

    // for update info
    public void setParentDistrictId(int id) {
        this.parentDistrictId = id;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    // for update info
    public void setCanonocalName(String cname) {
        this.canonicalName = cname;
    }

    public String getFullName() {
        return this.fullName;
    }

    // for update info
    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public HashSet<Integer> getNeighborsId() {
        return this.neighborsId;
    }

    // for update info
    public void setNeighborsId(HashSet<Integer> neighborsId) {
        this.neighborsId = neighborsId;
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

    // for update info
    public void setVotingData(VotingData votingData) {
        this.votingData = votingData;
    }

    public DemographicData getDemographicData() {
        return this.demographicData;
    }

    // for update info
    public void setDemographicData(DemographicData demographicData) {
        this.demographicData = demographicData;
    }

    public boolean getIsGhost() {
        return this.isGhost;
    }

    public void setGhost(boolean isGhost) {
        this.isGhost = isGhost;
    }

    @JsonIgnore
    public HashSet<PrecinctError> getAllError() {
        HashSet<PrecinctError> errorSet = new HashSet<PrecinctError>();
        for (Integer id : precinctErrors.keySet()) {
            errorSet.add(precinctErrors.get(id));
        }
        return errorSet;
    }

    public HashMap<Integer, PrecinctError> getPrecinctErrors() {
        return this.precinctErrors;
    }

    public void setPrecinctErrors(HashMap<Integer, PrecinctError> precinctErrors) {
        this.precinctErrors = precinctErrors;
    }

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

    public void updatePrecinct(Precinct info) {
        int infoId = info.getId();
        String infoCName = info.getCanonicalName();
        String infoFullName = info.getFullName();
        int infoParentId = info.getParentDistrictId();
        HashSet<Integer> infoNeighborsId = info.getNeighborsId();
        VotingData infoVD = info.getVotingData();
        DemographicData infoDD = info.getDemographicData();
        HashMap<Integer, PrecinctError> infoErrors = info.getPrecinctErrors();
        if (infoId != 0)
            this.setId(infoId);
        if (infoCName != null)
            this.setCanonocalName(infoCName);
        if (infoFullName != null)
            this.setFullName(infoFullName);
        if (infoParentId != 0)
            this.setParentDistrictId(infoParentId);
        if (infoNeighborsId != null)
            this.setNeighborsId(infoNeighborsId);
        if (infoVD != null)
            this.setVotingData(infoVD);
        if (infoDD != null)
            this.setDemographicData(infoDD);
        if (infoErrors != null)
            this.setPrecinctErrors(infoErrors);
    }
}
