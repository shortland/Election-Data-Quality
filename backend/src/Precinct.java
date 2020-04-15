import java.util.HashMap;
import java.util.HashSet;
import data_for_precinct.*;

// @Entity
// @Table(name = "PRECINTS")
public class Precinct {
    private int id;
    private String canonicalName;
    private String fullName;
    private int parentDistrictId;
    private VotingData votingData;
    private DemographicData demographicData;
    private HashSet<Integer> neigborsId;
    private HashMap<Integer, Error> precinctErrors;

    public Precinct(int id, String canonicalName, String fullName, int parentDistrictId, VotingData votingData,
            DemographicData demographicData) {
        this.id = id;
        this.canonicalName = canonicalName;
        this.fullName = fullName;
        this.parentDistrictId = parentDistrictId;
        this.votingData = votingData;
        this.demographicData = demographicData;
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

    public VotingData getVotingData() {
        return this.votingData;
    }

    public DemographicData getDemographicData() {
        return this.demographicData;
    }

    public String toString() {
        String str = "";
        str = str + "PrecinctId : " + this.getId() + "\n";
        str = str + "Name : " + this.getFullName() + " (" + this.getCanonicalName() + ")\n";
        str = str + "ParentId : " + this.getParentDistrictId() + "\n";
        str = str + this.getVotingData().toString() + this.getDemographicData().toString();
        return str;
    }

    public static void main(String[] args) {
        ElectionResults[] arrER = new ElectionResults[3];
        ElectionResults e = new ElectionResults(100, 200, 50, 50, ELECTIONS.PRES, 2016);
        ElectionResults e1 = new ElectionResults(400, 600, 500, 510, ELECTIONS.CONG, 2016);
        ElectionResults e2 = new ElectionResults(200, 230, 120, 150, ELECTIONS.CONG, 2018);
        // System.out.println(e.toString());
        // e.setVotes(PARTIES.REPUBLICAN, 300);
        // System.out.println(e.toString());
        arrER[0] = e;
        arrER[1] = e1;
        arrER[2] = e2;
        VotingData vd = new VotingData(arrER);
        DemographicData dd = new DemographicData(200, 200, 300, 300, 200);
        System.out.println(vd.toString());
        System.out.println(dd.toString());

        vd.getElectionData("2016PRES").setVotes(PARTIES.REPUBLICAN, 300);
        dd.setDemographic(RACE.ASIAN, 400);

        Precinct p = new Precinct(10409, "NPS", "NewPrecinct", 10, vd, dd);
        System.out.println(p.toString());
    }
}
