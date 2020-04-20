package com.electiondataquality.restservice.managers;

import java.util.HashMap;
import java.util.HashSet;

import com.electiondataquality.restservice.features.precinct.Precinct;

public class PrecinctManager {
    private HashMap<Integer, Precinct> precinctMap;

    // Deafult constructor
    public PrecinctManager() {
        this.precinctMap = new HashMap<Integer, Precinct>();
    }

    public PrecinctManager(HashSet<Precinct> precincts) {
        this.precinctMap = new HashMap<Integer, Precinct>();
        for (Precinct p : precincts) {
            this.precinctMap.put(p.getId(), p);
        }
    }

    // NOTE: this clears the map and populate the map with the precicntSet
    public void populate(HashSet<Precinct> precicntSet) {
        this.precinctMap.clear();
        for (Precinct p : precicntSet) {
            this.precinctMap.put(p.getId(), p);
        }
    }

    public Precinct getPrecicnt(int precinctId) {
        if (this.precinctMap.containsKey(precinctId)) {
            return this.precinctMap.get(precinctId);
        } else {
            System.out.println("precinct is null");
            return null;
        }
    }

    public void addPrecinct(Precinct p) {
        if (!this.precinctMap.containsKey(p.getId())) {
            this.precinctMap.put(p.getId(), p);
        }
    }

    public void deletePrecinct(int precinctId) {
        if (this.precinctMap.containsKey(precinctId)) {
            this.precinctMap.remove(precinctId);
        }
    }

    // DAVID_NOTE: probably don't neead this method
    public void updatePrecinct(int oldPrecinctId, Precinct newPrecinct) {
        this.deletePrecinct(oldPrecinctId);
        this.addPrecinct(newPrecinct);
    }

    public void setGhost(int precinctId, boolean isGhost) {
        if (this.precinctMap.containsKey(precinctId)) {
            this.precinctMap.get(precinctId).setGhost(isGhost);
        }
    }

    public String toString() {
        String str = "";
        for (int id : precinctMap.keySet()) {
            str = str + precinctMap.get(id).toString();
        }

        return str;
    }
}