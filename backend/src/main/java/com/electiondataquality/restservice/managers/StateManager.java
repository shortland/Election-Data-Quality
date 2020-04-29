package com.electiondataquality.restservice.managers;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import com.electiondataquality.features.state.State;

public class StateManager {

    private Map<String, State> stateMap;

    public StateManager() {
        this.stateMap = new HashMap<String, State>();
    }

    public StateManager(HashSet<State> stateSet) {
        this.stateMap = new HashMap<String, State>();

        for (State s : stateSet) {
            this.stateMap.put(s.getId(), s);
        }
    }

    public void populate(HashSet<State> stateSet) {
        this.stateMap.clear();

        for (State s : stateSet) {
            this.stateMap.put(s.getId(), s);
        }
    }

    public List<State> getAllStates() {
        List<State> allStates = new ArrayList<>();

        for (Map.Entry<String, State> state : this.stateMap.entrySet()) {
            allStates.add(state.getValue());
        }

        return allStates;
    }

    public State getState(String stateId) {
        if (this.stateMap.containsKey(stateId)) {
            return this.stateMap.get(stateId);
        }

        return null;
    }

    public Set<String> getAllCounties(String stateId) {
        if (this.stateMap.containsKey(stateId)) {
            return this.stateMap.get(stateId).getCountyIds();
        }

        return null;
    }

    public Set<String> getAllDistricts(String stateId) {
        if (this.stateMap.containsKey(stateId)) {
            return this.stateMap.get(stateId).getCongressionalDistrictIds();
        }

        return null;
    }

    public String toString() {
        String str = "";

        for (String id : this.stateMap.keySet()) {
            str = str + this.stateMap.get(id).toString() + "\n";
        }

        return str;
    }
}
