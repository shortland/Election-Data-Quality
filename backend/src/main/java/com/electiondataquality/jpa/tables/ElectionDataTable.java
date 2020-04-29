package com.electiondataquality.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.electiondataquality.restservice.voting.elections.ElectionResults;
import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;
import com.electiondataquality.restservice.voting.elections.enums.PARTIES;

@Entity
@Table(name = "election_data")
public class ElectionDataTable {

    @Id
    @Column(name = "data_id")
    private String dataId;

    @Column(name = "republican_vote")
    private int republican_vote;

    @Column(name = "democrat_vote")
    private int democrat_vote;

    @Column(name = "libratarian_vote")
    private int libratarian_vote;

    @Column(name = "other_vote")
    private int other_vote;

    @Enumerated(EnumType.STRING)
    @Column(name = "election")
    private ELECTIONS election;

    @Column(name = "precinct_idn")
    private String precinct_id;

    public ElectionDataTable() {

    }

    public ElectionDataTable(ElectionResults electionResults, String precinctId) {
        this.election = electionResults.getElection();
        this.precinct_id = precinctId;
        this.dataId = precinctId + "_" + this.election.name();
        this.republican_vote = electionResults.getResultByParty(PARTIES.REPUBLICAN);
        this.democrat_vote = electionResults.getResultByParty(PARTIES.DEMOCRAT);
        this.libratarian_vote = electionResults.getResultByParty(PARTIES.LIBRATARIAN);
        this.other_vote = electionResults.getResultByParty(PARTIES.OTHER);
    }

    public int getRepulican() {
        return this.republican_vote;
    }

    public void setRepublican(int republican_vote) {
        this.republican_vote = republican_vote;
    }

    public int getDemocrat() {
        return this.democrat_vote;
    }

    public void setDemocrat(int democrat_vote) {
        this.democrat_vote = democrat_vote;
    }

    public int getLibertarian() {
        return this.libratarian_vote;
    }

    public void setLibertarian(int libratarian_vote) {
        this.libratarian_vote = libratarian_vote;
    }

    public int getOther() {
        return this.other_vote;
    }

    public void setOther(int other_vote) {
        this.other_vote = other_vote;
    }

    public ELECTIONS getElection() {
        return this.election;
    }

    public void setElection(ELECTIONS election) {
        this.election = election;
    }

    public String getPrecinctId() {
        return this.precinct_id;
    }

    public void setPrecicntId(String precinct_id) {
        this.precinct_id = precinct_id;
    }

    public void update(ElectionResults electionResults, String precinctId) {
        this.democrat_vote = electionResults.getResultByParty(PARTIES.DEMOCRAT);
        this.republican_vote = electionResults.getResultByParty(PARTIES.REPUBLICAN);
        this.libratarian_vote = electionResults.getResultByParty(PARTIES.LIBRATARIAN);
        this.other_vote = electionResults.getResultByParty(PARTIES.OTHER);
        this.election = electionResults.getElection();
        this.precinct_id = precinctId;
        this.dataId = precinctId + "_" + electionResults.getElection().name();
    }

    public String toString() {
        return "PID : " + this.precinct_id + "ELECTION : " + this.election;
    }
}