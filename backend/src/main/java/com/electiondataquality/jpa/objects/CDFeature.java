package com.electiondataquality.jpa.objects;

import java.io.Serializable;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.electiondataquality.jpa.tables.FeatureTable;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Table(name = "congressional_districts")
public class CDFeature {

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

    public CDFeature() {
    }

    public CDFeature(int cdId, String name) {
        this.cdId = cdId;
        this.name = name;
        this.feature = null;
        this.childrenStr = "";
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

    public int getParentId() {
        return this.parentStateId;
    }

    public void setParentId(int id) {
        this.parentStateId = id;
    }

    public FeatureTable getFeature() {
        return this.feature;
    }

    public void setFeature(FeatureTable feature) {
        this.feature = feature;
    }

    public HashSet<Integer> childrenStrToArray() {
        String str = this.childrenStr.replaceAll("\\[|]", "");
        String[] childrens = str.split(",");
        HashSet<Integer> childrenPrecinctIds = new HashSet<Integer>();
        for (String idString : childrens) {
            childrenPrecinctIds.add(Integer.parseInt(idString));
        }
        return childrenPrecinctIds;
        // System.out.println(this.childrenStr);
        // System.out.println(childrenPrecinctIds);
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