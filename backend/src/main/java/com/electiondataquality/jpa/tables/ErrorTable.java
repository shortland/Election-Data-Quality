package com.electiondataquality.jpa.tables;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.electiondataquality.features.precinct.error.PrecinctError;
import com.electiondataquality.features.precinct.error.enums.ERROR_TYPE;

@Entity
@Table(name = "errors")
public class ErrorTable {

    @Id
    @Column(name = "idn")
    private int errorId;

    @Column(name = "feature_idn", insertable = false, updatable = false)
    private int featureId;

    @Enumerated(EnumType.STRING)
    private ERROR_TYPE errorType;

    @Column(name = "text")
    private String text;

    @Column(name = "created")
    private Date created;

    @Column(name = "resolved")
    private int resolved;

    @Column(name = "valid")
    private int valid;

    @Column(name = "precinct_idn")
    private String precinctId;

    public ErrorTable() {

    }

    public ErrorTable(int errorId, int featureId, String text, int resolved, int valid) {
        this.errorId = errorId;
        this.featureId = featureId;
        this.text = text;
        this.resolved = resolved;
        this.valid = valid;
    }

    public ErrorTable(PrecinctError pError) {
        this.errorId = pError.getId();
        this.text = pError.getText();
        if (pError.isResolved()) {
            this.resolved = 1;
        } else {
            this.resolved = 0;
        }
        this.errorType = pError.getErrorType();
    }

    public void update(PrecinctError pError, String precinctId) {
        if (pError.getId() != 0) {
            this.errorId = pError.getId();
        }
        if (pError.getText() != null) {
            this.text = pError.getText();
        }
        if (pError.isResolved()) {
            this.resolved = 1;
        } else {
            this.resolved = 0;
        }
        if (pError.getErrorType() != null) {
            this.errorType = pError.getErrorType();
        }
        this.precinctId = precinctId;
    }

    // public ErrorTable(int errorId, int featureId, String text, Date created, int
    // resolved, int valid) {
    // this.errorId = errorId;
    // this.featureId = featureId;
    // // this.errorType = errorType;
    // this.text = text;
    // this.created = created;
    // this.resolved = resolved;
    // this.valid = valid;
    // }

    public int getErrorId() {
        return errorId;
    }

    public void setErrorId(int errorId) {
        this.errorId = errorId;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
        this.featureId = featureId;
    }

    // public ERROR_TYPE getErrorType() {
    // return errorType;
    // }

    // public void setErrorType(ERROR_TYPE errorType) {
    // this.errorType = errorType;
    // }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getResolved() {
        return resolved;
    }

    public void setResolved(boolean isResolved) {
        if (isResolved) {
            this.resolved = 1;
        } else {
            this.resolved = 0;
        }
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getPrecinctId() {
        return this.precinctId;
    }

    public void setPrecinctId(String precinctId) {
        this.precinctId = precinctId;
    }

    @Override
    public String toString() {
        return "ErrorTable [created=" + created + ", errorId=" + errorId + ", errorType=;, featureId=" + featureId
                + ", resolved=" + resolved + ", text=" + text + ", valid=" + valid + "]";
    }
}
