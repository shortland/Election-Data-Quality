package data_for_precinct;
import java.util.HashMap;
import data_for_precinct.*;

public class VotingData {
    private HashMap<String, ElectionResults> electionData;
    private int precinctId;

    public VotingData(ElectionResults[] arrE) {
        this.electionData = new HashMap<String, ElectionResults>();
        for (int i = 0; i < arrE.length; i++) {
            ElectionResults currE = arrE[i];
            this.electionData.put(currE.getElectionAndYear(), currE);
        }
    }

    public String[] getAllElections() {
        String[] allElections = new String[this.electionData.size()];
        int counter = 0;
        for (String e : this.electionData.keySet()) {
            allElections[counter] = e;
            counter += 1;
        }
        return allElections;
    }

    public ElectionResults getElectionData(String e) {
        return this.electionData.get(e);
    }

    public void setElectionData(String e, ElectionResults er) {
        if (this.electionData.containsKey(e)) {
            this.electionData.remove(e); 
        }
        this.electionData.put(e, er);
    }

    public String toString() {
        String str = "";
        for (String e : electionData.keySet()) {
            str = str + e + ":\n" + electionData.get(e).toString() + "\n";
        }
        return str;
    }
}