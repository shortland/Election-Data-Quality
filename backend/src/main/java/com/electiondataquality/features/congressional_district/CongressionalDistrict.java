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

    private String parentStateId;

    private String cdId;

    private HashSet<String> childrenPrecincts;

    public CongressionalDistrict(CDFeature cdFeature) {
        this.cdId = cdFeature.getId();
        this.name = cdFeature.getName();
        this.parentStateId = cdFeature.getParentId();
        this.childrenPrecincts = cdFeature.childrenStrToArray();
        this.geometry = RawGeometryToShape.convertRawToGeometry(cdFeature.getFeature().getGeometry());
    }

    public CongressionalDistrict(String name, String parentStateId, String cdId, HashSet<String> childrenPrecincts,
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

    public String getId() {
        return this.cdId;
    }

    public String getParentId() {
        return this.parentStateId;
    }

    public void setParentId(String newParentId) {
        this.parentStateId = newParentId;
    }

    public HashSet<String> getChildrenId() {
        return this.childrenPrecincts;
    }

    public void addChildId(String newChildId) {
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
        String str = this.getName() + "\nID : " + this.getId() + "\nParent ID : " + this.getParentId() + "\nChildren : "
                + this.getChildrenId().toString();

        return str;
    }
}
