package com.electiondataquality.jpa.tables;

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

    @Column(name = "hispanic")
    private int hispanicPopulation;

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
        this.hispanicPopulation = demographic_data.getDemographicByRace(RACE.HISPANIC);
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

    public int getHispanicPopulation() {
        return this.hispanicPopulation;
    }

    public void setHispanicPopulation(int hispanicPopulation) {
        this.hispanicPopulation = hispanicPopulation;
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

    public void getWhitePopulation(int whitePopulation) {
        this.whitePopulation = whitePopulation;
    }

    public String toString() {
        return "Asian : " + Integer.toString(this.asianPopulation) + " Black : "
                + Integer.toString(this.blackPopulation) + " Hispanic : " + Integer.toString(this.hispanicPopulation)
                + " Other : " + Integer.toString(this.otherPopulation) + " White : "
                + Integer.toString(this.whitePopulation);
    }

}