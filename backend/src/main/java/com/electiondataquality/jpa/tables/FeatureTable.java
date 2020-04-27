package com.electiondataquality.jpa.tables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.OverridesAttribute;

import com.electiondataquality.features.Feature;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "features")
public class FeatureTable implements Serializable {
    @Id
    @Column(name = "idn")
    private int featureId;

    @Column(name = "geometry")
    private String geometry;

    public FeatureTable() {

    }

    public FeatureTable(int featureId, String geometry) {
        this.featureId = featureId;
        this.geometry = geometry;
    }

    public int getFeatureId() {
        return this.featureId;
    }

    public void setFeatureId(int id) {
        this.featureId = id;
    }

    public String getGeometry() {
        return this.geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    @Override
    public String toString() {
        return "ID : " + Integer.toString(this.featureId) + "\nGeometry" + this.geometry;
    }
}
