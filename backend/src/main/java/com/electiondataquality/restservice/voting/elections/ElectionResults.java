package com.electiondataquality.restservice.voting.elections;

import java.util.EnumMap;

import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;
import com.electiondataquality.restservice.voting.elections.enums.PARTIES;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ElectionResults {
    private EnumMap<PARTIES, Integer> resultsByParty;
    private PARTIES majorityParty;
    private int totalVoters;
    private ELECTIONS election;

    // Static methods

    public static ElectionResults mergeElectionResults(ElectionResults er1, ElectionResults er2) {
        if (er1.getElection() == er2.getElection()) {
            PARTIES[] allParties = { PARTIES.REPUBLICAN, PARTIES.DEMOCRAT, PARTIES.LIBRATARIAN, PARTIES.DEMOCRAT };
            int[] votes = new int[4];
            for (int i = 0; i < 4; i++) {
                votes[i] = er1.getResultByParty(allParties[i]) + er2.getResultByParty(allParties[i]);
            }
            ElectionResults mergedER = new ElectionResults(votes[0], votes[1], votes[2], votes[3], er1.getElection());
            return mergedER;
        } else {
            return null;
        }
    }

    public ElectionResults(int rVotes, int dVotes, int lVotes, int others, ELECTIONS election) {
        this.resultsByParty = new EnumMap<PARTIES, Integer>(PARTIES.class);
        this.resultsByParty.put(PARTIES.REPUBLICAN, rVotes);
        this.resultsByParty.put(PARTIES.DEMOCRAT, dVotes);
        this.resultsByParty.put(PARTIES.LIBRATARIAN, lVotes);
        this.resultsByParty.put(PARTIES.OTHER, others);
        this.majorityParty = this.findMajorityParty();
        this.totalVoters = rVotes + dVotes + lVotes + others;
        this.election = election;
    }

    private int calculateTotalVoters() {
        int total = 0;
        for (PARTIES p : this.resultsByParty.keySet()) {
            total += resultsByParty.get(p);
        }
        return total;
    }

    public PARTIES findMajorityParty() {
        PARTIES maxP = null;
        int max = 0;
        for (PARTIES p : this.resultsByParty.keySet()) {
            int cur = this.resultsByParty.get(p);
            if (cur > max) {
                max = cur;
                maxP = p;
            }
        }
        return maxP;
    }

    public PARTIES getMajorityParty() {
        return this.majorityParty;
    }

    public EnumMap<PARTIES, Integer> getResultsByParty() {
        return this.resultsByParty;
    }

    @JsonIgnore
    public ELECTIONS getElection() {
        return election;
    }

    @JsonIgnore
    public int getTotalVoters() {
        return this.totalVoters;
    }

    public int getResultByParty(PARTIES p) {
        if (this.resultsByParty.containsKey(p)) {
            return this.resultsByParty.get(p);
        } else {
            return 0;
        }
    }

    public double getResultPercentage(PARTIES p) {
        if (this.resultsByParty.containsKey(p)) {
            double percentage = Double.valueOf(this.resultsByParty.get(p)) / Double.valueOf(this.totalVoters);
            return percentage;
        } else {
            return 0.0;
        }
    }

    public void setVotes(PARTIES parties, int newVotes) {
        if (this.resultsByParty.containsKey(parties)) {
            this.resultsByParty.remove(parties);
        }
        this.resultsByParty.put(parties, newVotes);
        this.totalVoters = this.calculateTotalVoters();
        this.majorityParty = this.findMajorityParty();
    }

    public String toString() {
        String str = "";
        str = str + "\tMajority : " + this.getMajorityParty() + "\n";
        str = str + "\tTotal : " + this.getTotalVoters() + "\n";
        for (PARTIES party : this.resultsByParty.keySet()) {
            str = str + "\t\t" + party.name() + " : " + this.getResultByParty(party) + " ["
                    + this.getResultPercentage(party) + "]\n";
        }
        return str;
    }
}
