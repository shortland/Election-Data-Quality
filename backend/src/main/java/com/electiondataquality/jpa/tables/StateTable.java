package com.electiondataquality.jpa.tables;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.electiondataquality.features.state.State;
import com.electiondataquality.jpa.features.state.StateFeature;

@Entity
@Table(name = "states")
public class StateTable implements Serializable {
    @Id
    @Column(name = "state_idn")
    private int stateId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_abv")
    private String stateAbv;

    @OneToOne
    @JoinColumn(name = "feature_idn")
    private FeatureTable feature;

    public StateTable() {
    }

    public StateTable(int stateId, int featureId, String stateName, String stateAbv) {
        this.stateId = stateId;
        // this.featureId = featureId;
        this.stateName = stateName;
        this.stateAbv = stateAbv;
        this.feature = null;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    // public int getFeatureId() {
    // return featureId;
    // }

    // public void setFeatureId(int featureId) {
    // this.featureId = featureId;
    // }

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

    public FeatureTable getStateFeature() {
        if (this.feature != null) {
            return this.feature;
        } else {
            return null;
        }
    }

    public void setStateFeature(FeatureTable feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        if (this.feature != null) {
            return "States [stateAbv=" + stateAbv + ",stateId=" + stateId + ", stateName=" + stateName + "]\n"
                    + this.feature.toString();
        } else {
            return "States [stateAbv=" + stateAbv + ",stateId=" + stateId + ", stateName=" + stateName + "]";
        }
    }
}
