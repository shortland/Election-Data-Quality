package com.electiondataquality.jpa.tables;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Table(name = "states")
@Inheritance(strategy = InheritanceType.JOINED)
public class StateTable implements Serializable {
    private static final long serialVersionUID = -2891400626495806670L;

    @Id
    private int stateId;
    private int featureId;
    private String stateName;
    private String stateAbv;

    public StateTable(int stateId, int featureId, String stateName, String stateAbv) {
        super();

        this.stateId = stateId;
        this.featureId = featureId;
        this.stateName = stateName;
        this.stateAbv = stateAbv;
    }

    public StateTable() {
        super();
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
