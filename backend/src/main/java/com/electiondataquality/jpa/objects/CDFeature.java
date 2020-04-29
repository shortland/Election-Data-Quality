package com.electiondataquality.jpa.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.electiondataquality.jpa.tables.FeatureTable;

@Entity
@Table(name = "new_congressional_districts")
public class CDFeature {

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

    @OneToOne
    @JoinColumn(name = "feature_idn", insertable = false, updatable = false)
    private FeatureTable feature;

    public CDFeature() {
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
        return this.stateFips;
    }

    public void setParentId(int id) {
        this.stateFips = id;
    }

    public FeatureTable getFeature() {
        return this.feature;
    }

    public void setFeature(FeatureTable feature) {
        this.feature = feature;
    }

    // public HashSet<String> childrenStrToArray() {
    // String str = this.childrenStr.replaceAll("\\[|]", "");
    // String[] childrens = str.split(",");
    // HashSet<String> childrenPrecinctIds = new HashSet<String>();

    // for (String idString : childrens) {
    // childrenPrecinctIds.add(idString);
    // }

    // return childrenPrecinctIds;
    // }

    // public String toString() {
    // if (this.feature != null) {
    // return "Id: " + this.cdId + ", Name : " + this.name + "Parent Id : " +
    // this.parentStateId + "Feature : {\n"
    // + this.feature.toString() + "}";
    // } else {
    // return "Id: " + this.cdId + ", Name : " + this.name + "Parent Id : " +
    // this.parentStateId;
    // }
    // }
}
