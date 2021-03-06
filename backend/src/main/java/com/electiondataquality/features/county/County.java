package com.electiondataquality.features.county;

import com.electiondataquality.features.Feature;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.util.RawGeometryToShape;
import com.electiondataquality.jpa.tables.CountyTable;

public class County extends Feature {

    private String countyName;

    private String parentStateId;

    private String countyId;

    // constructor for JPA
    public County(CountyTable county) {
        this.countyId = county.getFipsCode();
        this.countyName = county.getCountyName();
        this.parentStateId = county.getStateIdn();
        this.geometry = RawGeometryToShape.convertRawToGeometry(county.getFeature().getGeometry());
    }

    public County(String countyName, String parentStateId, String countyId, MultiPolygon multiPolygon) {
        super(multiPolygon);

        this.countyName = countyName;
        this.parentStateId = parentStateId;
        this.countyId = countyId;
    }

    public String getName() {
        return this.countyName;
    }

    public String getId() {
        return this.countyId;
    }

    public String getParentId() {
        return this.parentStateId;
    }

    public void setParentId(String parentId) {
        this.parentStateId = parentId;
    }

    public String toString() {
        String str = this.countyName + "\nID : " + this.countyId + "\n Parent ID : " + this.parentStateId;

        return str;
    }
}
