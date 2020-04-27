package com.electiondataquality.features.congressional_district;

import java.util.HashSet;

import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.util.RawGeometryToShape;
import com.electiondataquality.jpa.objects.CDFeature;
import com.electiondataquality.features.Feature;

public class CongressionalDistrict extends Feature {

    private String name;

    private int parentStateId;

    private int cdId;

    private HashSet<Integer> childrenPrecincts;

    public CongressionalDistrict(CDFeature cdFeature) {
        this.cdId = cdFeature.getId();
        this.name = cdFeature.getName();
        this.parentStateId = cdFeature.getParentId();
        this.childrenPrecincts = cdFeature.childrenStrToArray();
        this.geometry = RawGeometryToShape.convertRawToGeometry(cdFeature.getFeature().getGeometry());
    }

    public CongressionalDistrict(String name, int parentStateId, int cdId, HashSet<Integer> childrenPrecincts,
            MultiPolygon multiPolygon) {
        super(multiPolygon);

        this.name = name;
        this.parentStateId = parentStateId;
        this.cdId = cdId;
        this.childrenPrecincts = childrenPrecincts;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.cdId;
    }

    public int getParentId() {
        return this.parentStateId;
    }

    public void setParentId(int newParentId) {
        this.parentStateId = newParentId;
    }

    public HashSet<Integer> getChildrenId() {
        return this.childrenPrecincts;
    }

    public void addChildId(int newChildId) {
        if (!this.childrenPrecincts.contains(newChildId)) {
            this.childrenPrecincts.add(newChildId);
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
        String str = this.getName() + "\nID : " + Integer.toString(this.getId()) + "\nParent ID : "
                + Integer.toString(this.getParentId()) + "\nChildren : " + this.getChildrenId().toString();

        return str;
    }
}
