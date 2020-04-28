package com.electiondataquality.jpa.tables;

import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
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

    @Column(name = "parent_state_idn")
    private int parentStateId;

    @Column(name = "children_precinct_idn")
    private String childrenStr;

    @Transient
    private HashSet<Integer> childrenPrecinctIds;

    public CongressionalDistrictTable() {
        this.childrenPrecinctIds = new HashSet<Integer>();
    }

    public CongressionalDistrictTable(int cdId, String name) {
        this.cdId = cdId;
        this.name = name;
        this.feature = null;
        this.childrenStr = "";
        this.childrenPrecinctIds = new HashSet<Integer>();
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

    public void childrenStrToArray() {
        String str = this.childrenStr.replaceAll("\\[|]", "");
        String[] childrens = str.split(",");

        for (String idString : childrens) {
            this.childrenPrecinctIds.add(Integer.parseInt(idString));
        }
    }

    public String toString() {
        if (this.feature != null) {
            return "Id: " + Integer.toString(this.cdId) + ", Name : " + this.name + "Parent Id : " + this.parentStateId
                    + "Feature : {\n" + this.feature.toString() + "}";
        } else {
            return "Id: " + Integer.toString(this.cdId) + ", Name : " + this.name + "Parent Id : " + this.parentStateId;
        }
    }
}
