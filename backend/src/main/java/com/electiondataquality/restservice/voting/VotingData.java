package com.electiondataquality.restservice.voting;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.electiondataquality.restservice.voting.elections.ElectionResults;

public class VotingData {

    private Map<ELECTIONS, ElectionResults> electionData;

    public static VotingData mergeVotingData(VotingData vd1, VotingData vd2) {
        Set<ELECTIONS> allElections;
        Set<ElectionResults> results = new HashSet<ElectionResults>();
        if (vd1.getAllElections().size() > vd2.getAllElections().size()) {
            allElections = vd1.getAllElections();
        } else {
            allElections = vd2.getAllElections();
        }

        for (ELECTIONS e : allElections) {
            ElectionResults er1 = vd1.getElectionData(e);
            ElectionResults er2 = vd2.getElectionData(e);

            if (er1 != null && er2 != null) {
                results.add(ElectionResults.mergeElectionResults(er1, er2));
            } else if (er1 == null && er2 != null) {
                results.add(er2);
            } else if (er1 != null && er2 == null) {
                results.add(er1);
            }
        }

        return new VotingData(results);
    }

    public VotingData() {
        this.electionData = new EnumMap<ELECTIONS, ElectionResults>(ELECTIONS.class);
    }

    public VotingData(Set<ElectionResults> erSet) {
        this.electionData = new EnumMap<ELECTIONS, ElectionResults>(ELECTIONS.class);

        for (ElectionResults er : erSet) {
            this.electionData.put(er.getElection(), er);
        }
    }

    @JsonIgnore
    public Set<ELECTIONS> getAllElections() {
        Set<ELECTIONS> allElections = new HashSet<ELECTIONS>();

        for (ELECTIONS e : this.electionData.keySet()) {
            allElections.add(e);
        }

        return allElections;
    }

    public Map<ELECTIONS, ElectionResults> getElectionData() {
        return this.electionData;
    }

    public ElectionResults getElectionData(ELECTIONS e) {
        if (this.electionData.containsKey(e)) {
            return this.electionData.get(e);
        }

        return null;
    }

    public void setElectionData(ELECTIONS e, ElectionResults er) {
        if (this.electionData.containsKey(e)) {
            this.electionData.remove(e);
        }

        er.setElection(e);
        this.electionData.put(e, er);
    }

    public String toString() {
        String str = "";

        for (ELECTIONS e : electionData.keySet()) {
            str = str + e.name() + ":\n" + electionData.get(e).toString() + "\n";
        }

        return str;
    }
}
