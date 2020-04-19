package com.electiondataquality.restservice.voting;

import java.util.EnumMap;

import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;
import com.electiondataquality.restservice.voting.elections.ElectionResults;

public class VotingData {
    private EnumMap<ELECTIONS, ElectionResults> electionData;
    private int precinctId;

    // static methods

    public static VotingData mergeVotingData(VotingData vd1, VotingData vd2) {
        ELECTIONS[] allElections;
        ElectionResults[] result;
        if (vd1.getAllElections().length > vd2.getAllElections().length) {
            allElections = vd1.getAllElections();
            result = new ElectionResults[vd1.getAllElections().length];
        } else {
            allElections = vd2.getAllElections();
            result = new ElectionResults[vd2.getAllElections().length];
        }
        int counter = 0;
        for (ELECTIONS e : allElections) {
            ElectionResults er1 = vd1.getElectionData(e);
            ElectionResults er2 = vd2.getElectionData(e);
            if (er1 != null && er2 != null) {
                result[counter] = ElectionResults.mergeElectionResults(er1, er2);
            } else if (er1 == null && er2 != null) {
                result[counter] = er2;
            } else if (er1 == null && er2 != null) {
                result[counter] = er1;
            }
            counter += 1;
        }
        VotingData mergedVD = new VotingData(result);
        return mergedVD;
    }

    public VotingData(ElectionResults[] arrE) {
        this.electionData = new EnumMap<ELECTIONS, ElectionResults>(ELECTIONS.class);
        for (int i = 0; i < arrE.length; i++) {
            ElectionResults currE = arrE[i];
            this.electionData.put(currE.getElection(), currE);
        }
    }

    public ELECTIONS[] getAllElections() {
        ELECTIONS[] allElections = new ELECTIONS[this.electionData.size()];
        int counter = 0;
        for (ELECTIONS e : this.electionData.keySet()) {
            allElections[counter] = e;
            counter += 1;
        }
        return allElections;
    }

    public ElectionResults getElectionData(ELECTIONS e) {
        if (this.electionData.containsKey(e)) {
            return this.electionData.get(e);
        } else {
            return null;
        }
    }

    public void setElectionData(ELECTIONS e, ElectionResults er) {
        if (this.electionData.containsKey(e)) {
            this.electionData.remove(e);
        }
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
