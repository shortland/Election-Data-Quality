package com.electiondataquality.restservice.managers;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;

import com.electiondataquality.features.precinct.Precinct;

public class PrecinctManager {

    private HashMap<String, Precinct> precinctMap;

    private HashMap<String, Precinct> originalPrecinctMap;

    public PrecinctManager() {
        this.precinctMap = new HashMap<String, Precinct>();
        this.originalPrecinctMap = new HashMap<String, Precinct>();
    }

    public PrecinctManager(HashSet<Precinct> precincts) {
        this.precinctMap = new HashMap<String, Precinct>();

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

    public Precinct getPrecinct(String precinctId) {
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

    public void deletePrecinct(String precinctId) {
        if (this.precinctMap.containsKey(precinctId)) {
            this.precinctMap.remove(precinctId);
        }
    }

    public Precinct getOriginalPrecinct(String precinctId) {
        if (this.originalPrecinctMap.containsKey(precinctId)) {
            return this.originalPrecinctMap.get(precinctId);
        }

        return null;
    }

    // NOTE: probably don't neead this method
    public void updatePrecinct(String oldPrecinctId, Precinct newPrecinct) {
        this.deletePrecinct(oldPrecinctId);
        this.addPrecinct(newPrecinct);
    }

    // TODO: Find a wat to
    public String getLargestPrecinctId() {
        BigInteger maxKey = new BigInteger("0");

        for (String currKey : this.precinctMap.keySet()) {
            BigInteger curr = new BigInteger(currKey);
            // -1 if max key is samller
            if (maxKey.compareTo(curr) == -1) {
                maxKey = curr;
            }
        }

        return new String(maxKey.toByteArray());
    }

    public void setGhost(String precinctId, boolean isGhost) {
        if (this.precinctMap.containsKey(precinctId)) {
            this.precinctMap.get(precinctId).setGhost(isGhost);
        }
    }

    public String toString() {
        String str = "";

        for (String id : precinctMap.keySet()) {
            str = str + precinctMap.get(id).toString();
        }

        return str;
    }
}
