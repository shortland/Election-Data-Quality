package com.electiondataquality.restservice.features.county;

import com.electiondataquality.restservice.features.Feature;
import com.electiondataquality.restservice.geometry.MultiPolygon;

public class County extends Feature {
    private String countyName;
    private int parentStateId;
    private int countyId;

    public County(String countyName, int parentStateId, int countyId, MultiPolygon multiPolygon) {
        super(multiPolygon);

        this.countyName = countyName;
        this.parentStateId = parentStateId;
        this.countyId = countyId;
    }

    public String getName() {
        return this.countyName;
    }

    public int getId() {
        return this.countyId;
    }

    public int getParentId() {
        return this.parentStateId;
    }

    public void setParentId(int parentId) {
        this.parentStateId = parentId;
    }

    public String toString() {
        String str = this.countyName + "\nID : " + Integer.toString(this.countyId) + "\n Parent ID : "
                + Integer.toString(this.parentStateId);

        return str;
    }
}
