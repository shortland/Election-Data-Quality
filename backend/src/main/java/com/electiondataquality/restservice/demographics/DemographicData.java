package com.electiondataquality.restservice.demographics;

import java.util.EnumMap;

import com.electiondataquality.restservice.demographics.enums.RACE;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DemographicData {

    private EnumMap<RACE, Integer> demographicByRace;

    private int total;

    public static DemographicData mergeDemographicData(DemographicData dd1, DemographicData dd2) {
        int[] population = new int[5];
        RACE[] rs = { RACE.ASIAN, RACE.BLACK, RACE.HISPANIC, RACE.OTHER, RACE.WHITE };

        for (int i = 0; i < rs.length; i++) {
            population[i] = dd1.getDemogaphic(rs[i]) + dd2.getDemogaphic(rs[i]);
        }

        DemographicData mergedDD = new DemographicData(population[0], population[1], population[2], population[3],
                population[4]);

        return mergedDD;
    }

    public DemographicData(int dA, int dB, int dH, int dO, int dW) {
        this.demographicByRace = new EnumMap<RACE, Integer>(RACE.class);
        this.demographicByRace.put(RACE.ASIAN, dA);
        this.demographicByRace.put(RACE.BLACK, dB);
        this.demographicByRace.put(RACE.HISPANIC, dH);
        this.demographicByRace.put(RACE.OTHER, dO);
        this.demographicByRace.put(RACE.WHITE, dW);

        calculateTotal();
    }

    public void calculateTotal() {
        int total = 0;

        for (RACE r : this.demographicByRace.keySet()) {
            total += this.demographicByRace.get(r);
        }

        this.total = total;
    }

    @JsonIgnore
    public int getTotal() {
        return this.total;
    }

    public EnumMap<RACE, Integer> getDemographicByRace() {
        return this.demographicByRace;
    }

    public int getDemogaphic(RACE race) {
        if (this.demographicByRace.containsKey(race)) {
            return this.demographicByRace.get(race);
        }

        return 0;
    }

    public double getDemogaphicPercentage(RACE race) {
        if (this.demographicByRace.containsKey(race)) {
            double percentage = Double.valueOf(this.getDemogaphic(race)) / Double.valueOf(this.total);

            return percentage;
        }

        // System.out.println("hi: " + race.name() + ":" +
        // Integer.toString(this.getDemogaphic(race)));

        return 0.0;
    }

    public void setDemographic(RACE race, int newData) {
        if (this.demographicByRace.containsKey(race)) {
            this.demographicByRace.remove(race);
        }

        this.demographicByRace.put(race, newData);
        this.calculateTotal();
    }

    public String toString() {
        String str = "Demographic : ";

        for (RACE r : this.demographicByRace.keySet()) {
            str = str + "\t" + r.name() + " : " + Integer.toString(this.getDemogaphic(r)) + " ["
                    + Double.toString(this.getDemogaphicPercentage(r)) + "]\n";
        }

        return str;
    }
}
