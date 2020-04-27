package com.electiondataquality.jpa.tables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "congressional_districts")
public class CongressionalDistrictTable implements Serializable {
    @Id
    @Column(name = "fips_code")
    private int cdId;

    @Column(name = "county_name")
    private String name;

    private FeatureTable feature;

    public CongressionalDistrictTable(int cdId, String name) {
        this.cdId = cdId;
        this.name = name;
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
}
