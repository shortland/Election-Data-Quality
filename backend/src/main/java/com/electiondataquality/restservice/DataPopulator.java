package com.electiondataquality.restservice;

import java.util.HashSet;

import com.electiondataquality.restservice.features.state.State;
import com.electiondataquality.restservice.managers.ServerManager;
import com.electiondataquality.restservice.features.congressional_district.CongressionalDistrict;
import com.electiondataquality.restservice.managers.CongressionalManager;

public class DataPopulator {
    private ServerManager serverManager;

    public DataPopulator(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    public void populateStates() {
        /**
         * POPULATE STATES
         */
        State a = new State(36, "New York", "NY", null, null, null);
        State b = new State(49, "Utah", "UT", null, null, null);
        State c = new State(55, "Wisconsin", "WI", null, null, null);

        HashSet<State> stateSet = new HashSet<State>();
        stateSet.add(a);
        stateSet.add(b);
        stateSet.add(c);

        this.serverManager.getStateManager().populate(stateSet);
    }

    public void populateCongressional() {
        CongressionalDistrict cd = new CongressionalDistrict("NEWCD", 1, 10, null, null);
        CongressionalDistrict cd1 = new CongressionalDistrict("CD1", 1, 11, null, null);
        HashSet<CongressionalDistrict> cdSet = new HashSet<CongressionalDistrict>();
        cdSet.add(cd);
        cdSet.add(cd1);

        this.serverManager.getCongressionalManager().populate(cdSet);
    }
}

/**
 * David's dummy data populator
 */
// public static void main(String[] args) {
// ElectionResults[] arrER = new ElectionResults[3];
// ElectionResults e = new ElectionResults(100, 200, 50, 50,
// ELECTIONS.PRES2016);
// ElectionResults e1 = new ElectionResults(400, 600, 500, 510,
// ELECTIONS.CONG2016);
// ElectionResults e2 = new ElectionResults(200, 230, 120, 150,
// ELECTIONS.CONG2018);
// arrER[0] = e;
// arrER[1] = e1;
// arrER[2] = e2;
// VotingData vd = new VotingData(arrER);

// // TEST: static mergeVotinData()
// // VotingData vd2 = new VotingData(arrER);
// // System.out.println(VotingData.mergeVotingData(vd,vd2).toString());

// DemographicData dd = new DemographicData(200, 200, 300, 300, 200);

// // TEST: static mergeDemographicData()
// // DemographicData dd2 = new DemographicData(100, 100, 100, 100, 100);
// //
// System.out.println(DemographicData.mergeDemographicData(dd,dd2).toString());

// // Test for setterMethod
// vd.getElectionData(ELECTIONS.PRES2016).setVotes(PARTIES.REPUBLICAN, 300);
// dd.setDemographic(RACE.ASIAN, 400);

// // create neighbor IDs
// HashSet<Integer> neighbor = new HashSet<Integer>();
// for (int i = 1; i < 5; i++) {
// neighbor.add(i);
// }

// // create Errors
// String[] arrstr = { "cool", "intersting", "no", "voting" };
// HashSet<Comment> comments = new HashSet<Comment>();
// for (int i = 1; i < 5; i++) {
// Comment c = new Comment(i, arrstr[i - 1]);
// comments.add(c);
// }
// PrecinctError er = new PrecinctError(ERROR_TYPE.NO_VOTERS, 1, "has no voter",
// false, comments);
// PrecinctError er1 = new PrecinctError(ERROR_TYPE.GHOST, 2, "is ghost", false,
// comments);
// HashSet<PrecinctError> errors = new HashSet<PrecinctError>();
// errors.add(er);
// errors.add(er1);

// Precinct p = new Precinct(10409, "NP", "NewPrecinct", 10, vd, dd, neighbor,
// errors);
// // System.out.println(p.toString());
// p.addNeighbor(10);
// p.deleteNeighbor(2);
// PrecinctError newError = new PrecinctError(ERROR_TYPE.NO_DEMOGRAPHICS, 3,
// "has no demo", false, comments);
// p.addError(newError);
// p.deleteError(2);
// System.out.println(p.toString());

// // TEST: precicntManager
// Precinct p1 = new Precinct(10222, "P1", "p1Precicnt", 10, vd, dd, neighbor,
// errors);
// HashSet<Precinct> precinctSet = new HashSet<Precinct>();
// precinctSet.add(p);
// precinctSet.add(p1);
// PrecinctManager pm = new PrecinctManager(precinctSet);
// // pm.updatePrecinct(10222,p);
// // System.out.println(pm.toString());

// // TEST: CongDistrictManager
// // TEST: congressionalDistirct
// HashSet<Integer> precincts = new HashSet<Integer>();
// precincts.add(201);
// precincts.add(333);
// CongressionalDistrict cd = new CongressionalDistrict("NEWCD", 1, 10,
// precincts);
// CongressionalDistrict cd1 = new CongressionalDistrict("CD1", 1, 11,
// precincts);
// HashSet<CongressionalDistrict> cdSet = new HashSet<CongressionalDistrict>();
// cdSet.add(cd);
// cdSet.add(cd1);
// CongDistrictManager cdmanager = new CongDistrictManager(cdSet);
// // System.out.println(cdmanager.toString());

// // TEST: stateManager
// State s = new State(10, "NewYork", "NY", precincts, precincts);
// // System.out.println(s.toString());
// State s1 = new State(11, "Utah", "UT", precincts, precincts);
// HashSet<State> stateSet = new HashSet<State>();
// stateSet.add(s);
// stateSet.add(s1);
// StateManager sm = new StateManager(stateSet);
// // System.out.println(sm.toString());
// }
