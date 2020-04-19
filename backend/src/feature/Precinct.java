package feature;

import java.util.HashMap;
import java.util.HashSet;

//import self created packages
import data_for_precinct.*;
import error.*;
import comment.*;

//TEST
import manager.*;

// @Entity
// @Table(name = "PRECINTS")
public class Precinct {
    private int id;
    private String canonicalName;
    private String fullName;
    private int parentDistrictId;
    private VotingData votingData;
    private DemographicData demographicData;
    private HashSet<Integer> neighborsId;
    private HashMap<Integer, PrecinctError> precinctErrors;
    private boolean isGhost;

    public Precinct(int id, String canonicalName, String fullName, int parentDistrictId, VotingData votingData,
            DemographicData demographicData, HashSet<Integer> neighborsId, HashSet<PrecinctError> errors) {
        this.id = id;
        this.canonicalName = canonicalName;
        this.fullName = fullName;
        this.parentDistrictId = parentDistrictId;
        this.votingData = votingData;
        this.demographicData = demographicData;
        this.isGhost = false;
        this.neighborsId = neighborsId;
        this.precinctErrors = this.populateErrorsMap(errors);
    }

    private HashMap<Integer, PrecinctError> populateErrorsMap(HashSet<PrecinctError> errors) {
        HashMap<Integer, PrecinctError> errorMap = new HashMap<Integer, PrecinctError>();
        for (PrecinctError e : errors) {
            errorMap.put(e.getId(), e);
        }
        return errorMap;
    }

    public int getId() {
        return this.id;
    }

    public int getParentDistrictId() {
        return this.parentDistrictId;
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public HashSet<Integer> getNeighborsId() {
        return this.neighborsId;
    }

    public boolean addNeighbor(int neighborId) {
        return this.neighborsId.add(neighborId);
    }

    public boolean deleteNeighbor(int neighborId) {
        return this.neighborsId.remove(neighborId);
    }

    public VotingData getVotingData() {
        return this.votingData;
    }

    public void setVotingData(VotingData votingData) {
        this.votingData = votingData;
    }

    public DemographicData getDemographicData() {
        return this.demographicData;
    }

    public void setDemographicData(DemographicData demographicData) {
        this.demographicData = demographicData;
    }

    public boolean isGhost() {
        return this.isGhost;
    }

    public void setGhost(boolean isGhost) {
        this.isGhost = isGhost;
    }

    public HashSet<PrecinctError> getAllError() {
        HashSet<PrecinctError> errorSet = new HashSet<PrecinctError>();
        for (Integer id : precinctErrors.keySet()) {
            errorSet.add(precinctErrors.get(id));
        }
        return errorSet;
    }

    public void addError(PrecinctError error) {
        int eId = error.getId();
        if (this.precinctErrors.containsKey(eId)) {
            this.precinctErrors.remove(eId);
        }
        this.precinctErrors.put(eId, error);
    }

    public void deleteError(int errorId) {
        if (this.precinctErrors.containsKey(errorId)) {
            this.precinctErrors.remove(errorId);
        }
    }

    public String toString() {
        String str = "";
        str = str + "PrecinctId : " + this.getId() + "\n";
        str = str + "Name : " + this.getFullName() + " (" + this.getCanonicalName() + ")\n";
        str = str + "ParentId : " + this.getParentDistrictId() + "\n";
        str = str + "NeighborsId : " + this.getNeighborsId().toString() + "\n";
        str = str + this.getVotingData().toString() + this.getDemographicData().toString();
        for (Integer eId : this.precinctErrors.keySet()) {
            str = str + this.precinctErrors.get(eId).toString() + "\n";
        }
        return str;
    }

    public static void main(String[] args) {
        ElectionResults[] arrER = new ElectionResults[3];
        ElectionResults e = new ElectionResults(100, 200, 50, 50, ELECTIONS.PRES2016);
        ElectionResults e1 = new ElectionResults(400, 600, 500, 510, ELECTIONS.CONG2016);
        ElectionResults e2 = new ElectionResults(200, 230, 120, 150, ELECTIONS.CONG2018);
        arrER[0] = e;
        arrER[1] = e1;
        arrER[2] = e2;
        VotingData vd = new VotingData(arrER);

        // TEST: static mergeVotinData()
        // VotingData vd2 = new VotingData(arrER);
        // System.out.println(VotingData.mergeVotingData(vd,vd2).toString());

        DemographicData dd = new DemographicData(200, 200, 300, 300, 200);

        // TEST: static mergeDemographicData()
        // DemographicData dd2 = new DemographicData(100, 100, 100, 100, 100);
        // System.out.println(DemographicData.mergeDemographicData(dd,dd2).toString());

        // Test for setterMethod
        vd.getElectionData(ELECTIONS.PRES2016).setVotes(PARTIES.REPUBLICAN, 300);
        dd.setDemographic(RACE.ASIAN, 400);

        // create neighbor IDs
        HashSet<Integer> neighbor = new HashSet<Integer>();
        for (int i = 1; i < 5; i++) {
            neighbor.add(i);
        }

        // create Errors
        String[] arrstr = { "cool", "intersting", "no", "voting" };
        HashSet<Comment> comments = new HashSet<Comment>();
        for (int i = 1; i < 5; i++) {
            Comment c = new Comment(i, arrstr[i - 1]);
            comments.add(c);
        }
        PrecinctError er = new PrecinctError(ERROR_TYPE.NO_VOTERS, 1, "has no voter", false, comments);
        PrecinctError er1 = new PrecinctError(ERROR_TYPE.GHOST, 2, "is ghost", false, comments);
        HashSet<PrecinctError> errors = new HashSet<PrecinctError>();
        errors.add(er);
        errors.add(er1);

        Precinct p = new Precinct(10409, "NP", "NewPrecinct", 10, vd, dd, neighbor, errors);
        // System.out.println(p.toString());
        p.addNeighbor(10);
        p.deleteNeighbor(2);
        PrecinctError newError = new PrecinctError(ERROR_TYPE.NO_DEMOGRAPHICS, 3, "has no demo", false, comments);
        p.addError(newError);
        p.deleteError(2);
        System.out.println(p.toString());

        // TEST: precicntManager
        Precinct p1 = new Precinct(10222, "P1", "p1Precicnt", 10, vd, dd, neighbor, errors);
        HashSet<Precinct> precinctSet = new HashSet<Precinct>();
        precinctSet.add(p);
        precinctSet.add(p1);
        PrecinctManager pm = new PrecinctManager(precinctSet);
        // pm.updatePrecinct(10222,p);
        // System.out.println(pm.toString());

        // TEST: CongDistrictManager
        // TEST: congressionalDistirct
        HashSet<Integer> precincts = new HashSet<Integer>();
        precincts.add(201);
        precincts.add(333);
        CongressionalDistrict cd = new CongressionalDistrict("NEWCD", 1, 10, precincts);
        CongressionalDistrict cd1 = new CongressionalDistrict("CD1", 1, 11, precincts);
        HashSet<CongressionalDistrict> cdSet = new HashSet<CongressionalDistrict>();
        cdSet.add(cd);
        cdSet.add(cd1);
        CongDistrictManager cdmanager = new CongDistrictManager(cdSet);
        // System.out.println(cdmanager.toString());

        // TEST: stateManager
        State s = new State(10, "NewYork", "NY", precincts, precincts);
        // System.out.println(s.toString());
        State s1 = new State(11, "Utah", "UT", precincts, precincts);
        HashSet<State> stateSet = new HashSet<State>();
        stateSet.add(s);
        stateSet.add(s1);
        StateManager sm = new StateManager(stateSet);
        // System.out.println(sm.toString());
    }
}
