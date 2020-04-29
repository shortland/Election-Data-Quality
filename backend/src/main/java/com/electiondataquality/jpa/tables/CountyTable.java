package com.electiondataquality.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "counties")
public class CountyTable {

    @Id
    @Column(name = "fips_code")
    private String fipsCode;

    @Column(name = "state_idn")
    private String stateIdn;

    @Column(name = "county_name")
    private String countyName;

    public CountyTable() {
    }

    public CountyTable(String fipsCode, String stateIdn, String countyName) {
        this.fipsCode = fipsCode;
        this.stateIdn = stateIdn;
        this.countyName = countyName;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public String getStateIdn() {
        return stateIdn;
    }

    public void setStateIdn(String stateIdn) {
        this.stateIdn = stateIdn;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    @Override
    public String toString() {
        return "CountyTable [countyName=" + countyName + ", fipsCode=" + fipsCode + ", stateIdn=" + stateIdn + "]";
    }
}
