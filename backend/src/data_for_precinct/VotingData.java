package data_for_precinct;
import java.util.EnumMap;
import data_for_precinct.*;

public class VotingData {
    private EnumMap<ELECTIONS, ElectionResults> electionData;
    private int precinctId;

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
        return this.electionData.get(e);
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