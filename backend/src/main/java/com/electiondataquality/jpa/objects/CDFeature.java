package com.electiondataquality.jpa.objects;

import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.electiondataquality.jpa.tables.FeatureTable;

@Entity
@Table(name = "congressional_districts")
public class CDFeature {

    @Id
    @Column(name = "fips_code")
    private String cdId;

    @Column(name = "county_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "feature_idn")
    private FeatureTable feature;

    @Column(name = "parent_state_idn")
    private String parentStateId;

    @Column(name = "children_precinct_idn")
    private String childrenStr;

    public CDFeature() {
    }

    public String getId() {
        return this.cdId;
    }

    public void setId(String cdId) {
        this.cdId = cdId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return this.parentStateId;
    }

    public void setParentId(String id) {
        this.parentStateId = id;
    }

    public FeatureTable getFeature() {
        return this.feature;
    }

    public void setFeature(FeatureTable feature) {
        this.feature = feature;
    }

    public HashSet<String> childrenStrToArray() {
        String str = this.childrenStr.replaceAll("\\[|]", "");
        String[] childrens = str.split(",");
        HashSet<String> childrenPrecinctIds = new HashSet<String>();

        for (String idString : childrens) {
            childrenPrecinctIds.add(idString);
        }

        return childrenPrecinctIds;
    }

    public String toString() {
        if (this.feature != null) {
            return "Id: " + this.cdId + ", Name : " + this.name + "Parent Id : " + this.parentStateId + "Feature : {\n"
                    + this.feature.toString() + "}";
        } else {
            return "Id: " + this.cdId + ", Name : " + this.name + "Parent Id : " + this.parentStateId;
        }
    }
}
