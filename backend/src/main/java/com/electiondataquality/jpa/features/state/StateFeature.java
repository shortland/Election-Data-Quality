package com.electiondataquality.jpa.features.state;

import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.electiondataquality.jpa.tables.StateTable;
import com.electiondataquality.features.Shape;
import com.electiondataquality.geometry.MultiPolygon;

@Entity
@Table(name = "features")
// public class StateFeature extends StateTable implements Shape {
public class StateFeature implements Shape {
    private static final long serialVersionUID = -3371319546756836374L;

    @Id
    @Column(name = "idn")
    private int featureId;

    private HashSet<Integer> counties;

    private HashSet<Integer> districts;

    @Column(name = "geometry")
    private String geometry;

    private MultiPolygon shape;

    public StateFeature(int featureId, HashSet<Integer> counties, HashSet<Integer> districts, MultiPolygon shape) {
        // super(stateId, featureId, stateName, stateAbv);
        this.featureId = featureId;
        this.counties = counties;
        this.districts = districts;
        this.shape = shape;
    }

    public StateFeature() {
        super();
    }

    public void setShape(MultiPolygon shape) {
        this.shape = shape;
    }

    public MultiPolygon getShape() {
        return this.shape;
    }

    public HashSet<Integer> getCounties() {
        return this.counties;
    }

    public void addCounty(int countyId) {
        if (!this.counties.contains(countyId)) {
            this.counties.add(countyId);
        }
    }

    public void deleteCounty(int countyId) {
        if (this.counties.contains(countyId)) {
            this.counties.remove(countyId);
        }
    }

    public HashSet<Integer> getDistricts() {
        return this.districts;
    }

    public void addDistrict(int districtId) {
        if (!this.districts.contains(districtId)) {
            this.districts.add(districtId);
        }
    }

    public void deleteDistrict(int districtId) {
        if (this.districts.contains(districtId)) {
            this.districts.remove(districtId);
        }
    }

    /**
     * TODO:
     * 
     * @return
     */
    // public VotingData computeVotingData() {
    // return null;
    // }

    /**
     * TODO:
     * 
     * @return
     */
    // public DemographicData computeDemographicData() {
    // return null;
    // }
}
