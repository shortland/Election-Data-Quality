package com.electiondataquality.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "new_congressional_districts")
public class CongressionalDistrictTable {

    @Id
    @Column(name = "idn")
    private int cdId;

    @Column(name = "name_lsad")
    private String name;

    @Column(name = "feature_idn")
    private int featureId;

    @Column(name = "state_fips")
    private int stateFips;

    @Column(name = "congressional_fips")
    private String congressionalFips;

    @Column(name = "geo_id")
    private String geoId;

    public CongressionalDistrictTable() {
    }

    public CongressionalDistrictTable(int cdId, String name, int featureId, int stateFips, String congressionalFips,
            String geoId) {
        this.cdId = cdId;
        this.name = name;
        this.featureId = featureId;
        this.stateFips = stateFips;
        this.congressionalFips = congressionalFips;
        this.geoId = geoId;
    }

    public int getCdId() {
        return cdId;
    }

    public void setCdId(int cdId) {
        this.cdId = cdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    public int getStateFips() {
        return stateFips;
    }

    public void setStateFips(int stateFips) {
        this.stateFips = stateFips;
    }

    public String getCongressionalFips() {
        return congressionalFips;
    }

    public void setCongressionalFips(String congressionalFips) {
        this.congressionalFips = congressionalFips;
    }

    public String getGeoId() {
        return geoId;
    }

    public void setGeoId(String geoId) {
        this.geoId = geoId;
    }

    @Override
    public String toString() {
        return "CongressionalDistrictTable [cdId=" + cdId + ", congressionalFips=" + congressionalFips + ", featureId="
                + featureId + ", geoId=" + geoId + ", name=" + name + ", stateFips=" + stateFips + "]";
    }
}
