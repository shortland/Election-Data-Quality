package com.electiondataquality.jpa.tables;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "states")
public class StateTable implements Serializable {
    @Id
    @Column(name = "state_idn")
    private int stateId;

    @Column(name = "feature_idn")
    private int featureId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_abv")
    private String stateAbv;

    public StateTable() {
    }

    public StateTable(int stateId, int featureId, String stateName, String stateAbv) {
        this.stateId = stateId;
        this.featureId = featureId;
        this.stateName = stateName;
        this.stateAbv = stateAbv;
    }

    public int getStateId() {
        return stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
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

    @Override
    public String toString() {
        return "States [featureId=" + featureId + ", stateAbv=" + stateAbv + ", stateId=" + stateId + ", stateName="
                + stateName + "]";
    }
}
