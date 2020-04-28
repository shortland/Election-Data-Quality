package com.electiondataquality.restservice.managers;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

import com.electiondataquality.features.state.State;

public class StateManager {

    private HashMap<Integer, State> stateMap;

    public StateManager() {
        this.stateMap = new HashMap<Integer, State>();
    }

    public StateManager(HashSet<State> stateSet) {
        this.stateMap = new HashMap<Integer, State>();

        for (State s : stateSet) {
            this.stateMap.put(s.getId(), s);
        }
    }

    // NOTE: this clears the map and populate the map with the stateSet
    public void populate(HashSet<State> stateSet) {
        this.stateMap.clear();

        for (State s : stateSet) {
            this.stateMap.put(s.getId(), s);
        }
    }

    public ArrayList<State> getAllStates() {
        ArrayList<State> allStates = new ArrayList<>();

        for (Map.Entry<Integer, State> state : this.stateMap.entrySet()) {
            allStates.add(state.getValue());
        }

        return allStates;
    }

    public State getState(int stateId) {
        if (this.stateMap.containsKey(stateId)) {
            return this.stateMap.get(stateId);
        }

        return null;
    }

    public HashSet<Integer> getAllCounties(int stateId) {
        if (this.stateMap.containsKey(stateId)) {
            return this.stateMap.get(stateId).getCountiesId();
        }

        return null;
    }

    public HashSet<Integer> getAllDistricts(int stateId) {
        if (this.stateMap.containsKey(stateId)) {
            return this.stateMap.get(stateId).getDistrictsId();
        }

        return null;
    }

    public String toString() {
        String str = "";

        for (int id : this.stateMap.keySet()) {
            str = str + this.stateMap.get(id).toString() + "\n";
        }

        return str;
    }
}
