package com.electiondataquality.restservice.demographics;

import java.util.EnumMap;
import java.util.Map;

import com.electiondataquality.restservice.demographics.enums.RACE;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DemographicData {

    private Map<RACE, Integer> demographicByRace;

    // private int total;

    public static DemographicData mergeDemographicData(DemographicData dd1, DemographicData dd2) {
        int[] population = new int[6];
        RACE[] rs = { RACE.ASIAN, RACE.BLACK, RACE.NATIVE_AMERICAN, RACE.NATIVE_HAWAIIAN, RACE.OTHER, RACE.WHITE };

        for (int i = 0; i < rs.length; i++) {
            population[i] = dd1.getDemographicByRace(rs[i]) + dd2.getDemographicByRace(rs[i]);
        }

        DemographicData mergedDD = new DemographicData(population[0], population[1], population[2], population[3],
                population[4], population[5]);

        return mergedDD;
    }

    public DemographicData(int dA, int dB, int dNA, int dNH, int dO, int dW) {
        this.demographicByRace = new EnumMap<RACE, Integer>(RACE.class);
        this.demographicByRace.put(RACE.ASIAN, dA);
        this.demographicByRace.put(RACE.BLACK, dB);
        this.demographicByRace.put(RACE.NATIVE_AMERICAN, dNA);
        this.demographicByRace.put(RACE.NATIVE_HAWAIIAN, dNH);
        this.demographicByRace.put(RACE.OTHER, dO);
        this.demographicByRace.put(RACE.WHITE, dW);

        // calculateTotal();
    }

    // public void calculateTotal() {
    // int total = 0;

    // for (RACE r : this.demographicByRace.keySet()) {
    // total += this.demographicByRace.get(r);
    // }

    // this.total = total;
    // }

    // @JsonIgnore
    // public int getTotal() {
    // return this.total;
    // }

    public Map<RACE, Integer> getDemographic() {
        return this.demographicByRace;
    }

    public int getDemographicByRace(RACE race) {
        if (this.demographicByRace.containsKey(race)) {
            return this.demographicByRace.get(race);
        }

        return 0;
    }

    // public double getDemogaphicPercentage(RACE race) {
    // if (this.demographicByRace.containsKey(race)) {
    // double percentage = Double.valueOf(this.getDemographicByRace(race)) /
    // Double.valueOf(this.total);

    // return percentage;
    // }

    // // System.out.println("hi: " + race.name() + ":" +
    // // Integer.toString(this.getDemogaphic(race)));

    // return 0.0;
    // }

    public void setDemographic(RACE race, int newData) {
        if (this.demographicByRace.containsKey(race)) {
            this.demographicByRace.remove(race);
        }

        this.demographicByRace.put(race, newData);
        // this.calculateTotal();
    }

    public String toString() {
        String str = "Demographic : ";

        for (RACE r : this.demographicByRace.keySet()) {
            str = str + "\t" + r.name() + " : " + Integer.toString(this.getDemographicByRace(r)) + "\n";
        }

        return str;
    }
}
