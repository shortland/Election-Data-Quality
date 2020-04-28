package com.electiondataquality.restservice.managers;

import java.util.HashMap;
import java.util.HashSet;

import com.electiondataquality.features.precinct.Precinct;

public class PrecinctManager {

    private HashMap<Integer, Precinct> precinctMap;

    private HashMap<Integer, Precinct> originalPrecinctMap;

    public PrecinctManager() {
        this.precinctMap = new HashMap<Integer, Precinct>();
        this.originalPrecinctMap = new HashMap<Integer, Precinct>();
    }

    public PrecinctManager(HashSet<Precinct> precincts) {
        this.precinctMap = new HashMap<Integer, Precinct>();

        for (Precinct p : precincts) {
            this.precinctMap.put(p.getId(), p);
        }

        for (Precinct p : precincts) {
            this.originalPrecinctMap.put(p.getId(), p);
        }
    }

    // NOTE: this clears the map and populate the map with the precicntSet
    public void populate(HashSet<Precinct> precicntSet) {
        this.precinctMap.clear();
        this.originalPrecinctMap.clear();

        for (Precinct p : precicntSet) {
            this.precinctMap.put(p.getId(), p);
            this.originalPrecinctMap.put(p.getId(), Precinct.copyPrecinct(p));
        }
    }

    public Precinct getPrecinct(int precinctId) {
        if (this.precinctMap.containsKey(precinctId)) {
            return this.precinctMap.get(precinctId);
        }

        return null;
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

    public Precinct getOriginalPrecinct(int precinctId) {
        if (this.originalPrecinctMap.containsKey(precinctId)) {
            return this.originalPrecinctMap.get(precinctId);
        }

        return null;
    }

    // NOTE: probably don't neead this method
    public void updatePrecinct(int oldPrecinctId, Precinct newPrecinct) {
        this.deletePrecinct(oldPrecinctId);
        this.addPrecinct(newPrecinct);
    }

    public int getLargestPrecinctId() {
        int maxKey = 0;

        for (int currKey : this.precinctMap.keySet()) {
            if (maxKey < currKey) {
                maxKey = currKey;
            }
        }

        return maxKey;
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
