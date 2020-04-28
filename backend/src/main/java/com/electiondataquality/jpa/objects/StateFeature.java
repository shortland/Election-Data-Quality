package com.electiondataquality.jpa.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.electiondataquality.jpa.tables.FeatureTable;

@Entity
@Table(name = "states")
public class StateFeature {

    @Id
    @Column(name = "state_idn")
    private String stateId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_abv")
    private String stateAbv;

    @OneToOne
    @JoinColumn(name = "feature_idn")
    private FeatureTable feature;

    public StateFeature() {
    }

    public StateFeature(String stateId, String stateName, String stateAbv) {
        this.stateId = stateId;
        this.stateName = stateName;
        this.stateAbv = stateAbv;
        this.feature = null;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateAbv() {
        return stateAbv;
    }

    public void setStateAbv(String stateAbv) {
        this.stateAbv = stateAbv;
    }

    public FeatureTable getFeature() {
        return this.feature;
    }

    public void setFeature(FeatureTable feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        return "States [stateAbv=" + stateAbv + ",stateId=" + stateId + ",stateName=" + stateName + "]\n"
                + this.feature.toString();
    }
}
