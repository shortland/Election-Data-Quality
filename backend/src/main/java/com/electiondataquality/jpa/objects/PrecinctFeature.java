package com.electiondataquality.jpa.objects;

import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.electiondataquality.jpa.tables.FeatureTable;

@Entity
@Table(name = "precincts")
public class PrecinctFeature {

    @Id
    @Column(name = "precinct_idn")
    private int id;
    // @Column(name = "")
    // private String canonicalName;
    @Column(name = "fullname")
    private String fullName;

    @Column(name = "county_fips_code")
    private int parentDistrictId;

    @Column(name = "neighbors_idn")
    private String neighborsId;

    @Column(name = "errors_idn")
    private String errorsId;

    @OneToOne
    @JoinColumn(name = "feature_idn")
    private FeatureTable feature;

    public PrecinctFeature() {

    }

    public PrecinctFeature(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getParentDistrictId() {
        return this.parentDistrictId;
    }

    public void setParentDistrictId(int parentDistrictId) {
        this.parentDistrictId = parentDistrictId;
    }

    public FeatureTable getFeature() {
        return this.feature;
    }

    public void setFeature(FeatureTable feature) {
        this.feature = feature;
    }

    public HashSet<Integer> getNeighborsIdSet() {
        String str = this.neighborsId.replaceAll("\\[|]", "");
        String[] neighbors = str.split(",");
        HashSet<Integer> neighborsIdSet = new HashSet<Integer>();
        for (String idString : neighbors) {
            neighborsIdSet.add(Integer.parseInt(idString));
        }
        return neighborsIdSet;
    }

    public HashSet<Integer> getErrorIdSet() {
        String str = this.errorsId.replaceAll("\\[|]", "");
        String[] errors = str.split(",");
        HashSet<Integer> errorIdSet = new HashSet<Integer>();
        for (String idString : errors) {
            errorIdSet.add(Integer.parseInt(idString));
        }
        return errorIdSet;
    }

    public String toString() {
        return "Id : " + Integer.toString(this.id) + " Name : " + this.fullName + "Parent ID : " + this.parentDistrictId
                + "Feature" + this.feature.toString();
    }

    // private VotingData votingData;

    // private DemographicData demographicData;

    // private HashSet<Integer> neighborsId;

    // private HashMap<Integer, PrecinctError> precinctErrors;

    // private boolean isGhost;
}