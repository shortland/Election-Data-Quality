package com.electiondataquality.jpa.tables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "election")
    private String election;

    @Column(name = "precinct_idn")
    private int precinct_id;

    public ElectionDataTable() {

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

    public String getElection() {
        return this.election;
    }

    public void setElection(String election) {
        this.election = election;
    }

    public int getPrecinctId() {
        return this.precinct_id;
    }

    public void setPrecicntId(int precinct_id) {
        this.precinct_id = precinct_id;
    }

    public String toString() {
        return "PID : " + Integer.toString(this.precinct_id) + "ELECTION : " + this.election;
    }
}