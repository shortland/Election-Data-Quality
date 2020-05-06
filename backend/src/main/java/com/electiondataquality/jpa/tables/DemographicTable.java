package com.electiondataquality.jpa.tables;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.demographics.enums.RACE;

@Entity
@Table(name = "demographic_data")
public class DemographicTable {

    @Id
    @Column(name = "precinct_idn")
    private String precicntId;

    @Column(name = "asian")
    private int asianPopulation;

    @Column(name = "black")
    private int blackPopulation;

    @Column(name = "native_american")
    private int naPopulation;

    @Column(name = "native_hawaiian")
    private int nhPopulation;

    @Column(name = "other")
    private int otherPopulation;

    @Column(name = "white")
    private int whitePopulation;

    public DemographicTable() {
    }

    public DemographicTable(DemographicData demographic_data, String precinctId) {
        this.precicntId = precinctId;
        this.asianPopulation = demographic_data.getDemographicByRace(RACE.ASIAN);
        this.blackPopulation = demographic_data.getDemographicByRace(RACE.BLACK);
        this.naPopulation = demographic_data.getDemographicByRace(RACE.NATIVE_AMERICAN);
        this.nhPopulation = demographic_data.getDemographicByRace(RACE.NATIVE_HAWAIIAN);
        this.otherPopulation = demographic_data.getDemographicByRace(RACE.OTHER);
        this.whitePopulation = demographic_data.getDemographicByRace(RACE.WHITE);
    }

    public int getAsianPopulation() {
        return this.asianPopulation;
    }

    public void setAsianPopulation(int asianPopulation) {
        this.asianPopulation = asianPopulation;
    }

    public int getBlackPopulation() {
        return this.blackPopulation;
    }

    public void setBlackPopulation(int blackPopulation) {
        this.blackPopulation = blackPopulation;
    }

    public int getNativeAmericanPopulation() {
        return this.naPopulation;
    }

    public void setNativeAmericanPopulation(int naPopulation) {
        this.naPopulation = naPopulation;
    }

    public int getNativeHawaiianPopulation() {
        return this.nhPopulation;
    }

    public void setNativeHawaiianPopulation(int nhPopulation) {
        this.nhPopulation = nhPopulation;
    }

    public int getOtherPopulation() {
        return this.otherPopulation;
    }

    public void setOtherPopulation(int otherPopulation) {
        this.otherPopulation = otherPopulation;
    }

    public int getWhitePopulation() {
        return this.whitePopulation;
    }

    public void setWhitePopulation(int whitePopulation) {
        this.whitePopulation = whitePopulation;
    }

    public void setPrecinctId(String precinctId) {
        this.precicntId = precinctId;
    }

    public void update(DemographicData demographicData, String precinctId) {
        this.setAsianPopulation(demographicData.getDemographicByRace(RACE.ASIAN));
        this.setBlackPopulation(demographicData.getDemographicByRace(RACE.BLACK));
        this.setNativeAmericanPopulation(demographicData.getDemographicByRace(RACE.NATIVE_AMERICAN));
        this.setNativeHawaiianPopulation(demographicData.getDemographicByRace(RACE.NATIVE_HAWAIIAN));
        this.setOtherPopulation(demographicData.getDemographicByRace(RACE.OTHER));
        this.setWhitePopulation(demographicData.getDemographicByRace(RACE.WHITE));
        this.setPrecinctId(precinctId);
    }

    public void update(Map<String, Integer> demographicData, String precinctId) {
        if (demographicData.containsKey("ASIAN")) {
            this.setAsianPopulation(demographicData.get("ASIAN"));
        }
        if (demographicData.containsKey("BLACK")) {
            this.setBlackPopulation(demographicData.get("BLACK"));
        }
        if (demographicData.containsKey("NATIVE_AMERICAN")) {
            this.setNativeAmericanPopulation(demographicData.get("NATIVE_AMERICAN"));
        }
        if (demographicData.containsKey("NATIVE_HAWAIIAN")) {
            this.setNativeHawaiianPopulation(demographicData.get("NATIVE_HAWAIIAN"));
        }
        if (demographicData.containsKey("OTHER")) {
            this.setOtherPopulation(demographicData.get("OTHER"));
        }
        if (demographicData.containsKey("WHITE")) {
            this.setWhitePopulation(demographicData.get("WHITE"));
        }
        this.setPrecinctId(precinctId);
    }

    // public String toString() {
    // return "Asian : " + Integer.toString(this.asianPopulation) + " Black : "
    // + Integer.toString(this.blackPopulation) + " Hispanic : " +
    // Integer.toString(this.hispanicPopulation)
    // + " Other : " + Integer.toString(this.otherPopulation) + " White : "
    // + Integer.toString(this.whitePopulation);
    // }
}
