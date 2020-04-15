package data_for_precinct;
import java.util.EnumMap;
import data_for_precinct.*;

public class ElectionResults {
    private EnumMap<PARTIES, Integer> resultsByParty;
    private PARTIES majorityParty;
    private int totalVoters;
    private ELECTIONS election;
    private int year;

    public ElectionResults(int rVotes, int dVotes, int lVotes, int others, ELECTIONS election, int year) {
        this.resultsByParty = new EnumMap<PARTIES, Integer>(PARTIES.class);
        this.resultsByParty.put(PARTIES.REPUBLICAN, rVotes);
        this.resultsByParty.put(PARTIES.DEMOCRAT, dVotes);
        this.resultsByParty.put(PARTIES.LIBRATARIAN, lVotes);
        this.resultsByParty.put(PARTIES.OTHER, others);
        this.majorityParty = this.findMajorityParty();
        this.totalVoters = rVotes + dVotes + lVotes + others;
        this.election = election;
        this.year = year;
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

    public int getTotalVoters() {
        return this.totalVoters;
    }

    public int getResultByParty(PARTIES p) {
        if (this.resultsByParty.containsKey(p)){
            return this.resultsByParty.get(p);
        }
        else{
            return 0;
        }
    }
    
    public double getResultPercentage(PARTIES p){
        if (this.resultsByParty.containsKey(p)){
            double percentage = Double.valueOf(this.resultsByParty.get(p))/Double.valueOf(this.totalVoters);
            return percentage;
        }
        else{
            return 0.0;
        }
    }

    public String getElectionAndYear() {
        String str = Integer.toString(this.year) + this.election.name();
        return str;
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
        for (PARTIES party : this.resultsByParty.keySet()){
            str = str + "\t\t" + party.name() + " : " +this.getResultByParty(party) +" ["+this.getResultPercentage(party) +"]\n";
        }
        return str;
    }

}