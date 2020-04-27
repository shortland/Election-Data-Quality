package com.electiondataquality.jpa.tables;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name = "congressional_districts")
public class CongressionalDistrictTable {
  
    @Id
    @Column(name = "fips_code")
    private int cdId;

    @Column(name = "county_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "feature_idn")
    private FeatureTable feature;

    public CongressionalDistrictTable(int cdId, String name) {
        this.cdId = cdId;
        this.name = name;
        this.feature = null;
    }

    public int getId() {
        return this.cdId;
    }

    public void setId(int cdId) {
        this.cdId = cdId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FeatureTable getFeature() {
        return this.feature;
    }

    public void setFeature(FeatureTable feature) {
        this.feature = feature;
    }

    public String toString() {
        if (this.feature != null) {
            return "Id: " + Integer.toString(this.cdId) + ", Name : " + this.name + "Feature : "
                    + this.feature.toString();
        } else {
            return "Id: " + Integer.toString(this.cdId) + ", Name : " + this.name;
        }
    }
}
