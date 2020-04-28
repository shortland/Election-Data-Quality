package com.electiondataquality.jpa.objects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.features.precinct.error.PrecinctError;
import com.electiondataquality.jpa.tables.DemographicTable;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.jpa.tables.ElectionDataTable;
import com.electiondataquality.jpa.tables.FeatureTable;
import com.electiondataquality.restservice.demographics.DemographicData;
import com.electiondataquality.restservice.voting.VotingData;
import com.electiondataquality.restservice.voting.elections.ElectionResults;
import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;

@Entity
@Table(name = "precincts")
public class PrecinctFeature {

    @Id
    @Column(name = "precinct_idn")
    private String id;
    // @Column(name = "")
    // private String canonicalName;
    @Column(name = "fullname")
    private String fullName;

    @Column(name = "county_fips_code")
    private String parentDistrictId;

    @Column(name = "neighbors_idn")
    private String neighborsId;

    @Column(name = "errors_idn")
    private String errorsId;

    @OneToOne
    @JoinColumn(name = "feature_idn")
    private FeatureTable feature;

    @OneToOne
    @JoinColumn(name = "precinct_idn")
    private DemographicTable demographic;

    @OneToMany
    @JoinColumn(name = "precinct_idn")
    private Set<ElectionDataTable> electionDataTableSet;

    public PrecinctFeature() {
        // this.errors = new;
    }

    // TODO: for data to be store back to the database
    public PrecinctFeature(Precinct precinct) {
        this.id = precinct.getId();
        this.fullName = precinct.getFullName();
        this.parentDistrictId = precinct.getParentDistrictId();
        this.demographic = new DemographicTable(precinct.getDemographicData(), precinct.getId());
        this.electionDataTableSet = new HashSet<ElectionDataTable>();
        for (ELECTIONS e : precinct.getVotingData().getAllElections()) {
            ElectionDataTable edt = new ElectionDataTable(precinct.getVotingData().getElectionData(e),
                    precinct.getId());
            this.electionDataTableSet.add(edt);
        }
        HashSet<PrecinctError> allErrors = precinct.getAllError();
        for (PrecinctError pe : allErrors) {
            ErrorTable et = new ErrorTable(pe.getId(), featureId, text, resolved, valid)
        }
        // feature
        // errorsId
    }

    public PrecinctFeature(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;

        // this.errors = new ArrayList<ErrorTable>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getParentDistrictId() {
        return this.parentDistrictId;
    }

    public void setParentDistrictId(String parentDistrictId) {
        this.parentDistrictId = parentDistrictId;
    }

    public FeatureTable getFeature() {
        return this.feature;
    }

    public void setFeature(FeatureTable feature) {
        this.feature = feature;
    }

    public DemographicTable getDemogrpahicTable() {
        return this.demographic;
    }

    public void setDemographicTable(DemographicTable demographic) {
        this.demographic = demographic;
    }

    public Set<ElectionDataTable> getElectionDataSet() {
        return this.electionDataTableSet;
    }

    public void addElecionalDataTable(ElectionDataTable electionDataTable) {
        this.electionDataTableSet.add(electionDataTable);
    }

    public void removeElectionalDataTable(String election, String precicnt_idn) {
        for (ElectionDataTable edt : this.electionDataTableSet) {
            if (edt.getElection().equals(election) && edt.getPrecinctId().equals(precicnt_idn)) {
                this.electionDataTableSet.remove(edt);
            }
        }
    }

    public HashSet<String> getNeighborsIdSet() {
        String str = this.neighborsId.replaceAll("\\[|]", "");
        String[] neighbors = str.split(",");
        HashSet<String> neighborsIdSet = new HashSet<String>();
        for (String idString : neighbors) {
            neighborsIdSet.add(idString);
        }
        return neighborsIdSet;
    }

    public HashSet<Integer> getErrorIdSet() {
        String str = this.errorsId.replaceAll("\\[|]", "");
        String[] errors = str.split(",");
        HashSet<Integer> errorIdSet = new HashSet<Integer>();
        for (String idString : errors) {
            errorIdSet.add(Integer.parseInt(idString));
        }
        return errorIdSet;
    }

    public DemographicData getDemographicData() {
        if (demographic != null) {
            DemographicData data = new DemographicData(this.demographic.getAsianPopulation(),
                    this.demographic.getBlackPopulation(), this.demographic.getHispanicPopulation(),
                    this.demographic.getOtherPopulation(), this.demographic.getWhitePopulation());
            return data;
        } else {
            return null;
        }
    }

    public void printErrorTable() {
        for (ErrorTable er : this.feature.getErrors()) {
            System.out.println(er.toString());
        }
    }

    private ElectionResults convertToElectionResult(ElectionDataTable electionDataTable) {
        String election = electionDataTable.getElection();
        if (election.equals("PRES2016")) {
            ElectionResults result = new ElectionResults(electionDataTable.getRepulican(),
                    electionDataTable.getDemocrat(), electionDataTable.getLibertarian(), electionDataTable.getOther(),
                    ELECTIONS.PRES2016);
            return result;
        } else if (election.equals("CONG2016")) {
            ElectionResults result = new ElectionResults(electionDataTable.getRepulican(),
                    electionDataTable.getDemocrat(), electionDataTable.getLibertarian(), electionDataTable.getOther(),
                    ELECTIONS.CONG2016);
            return result;
        } else if (election.equals("CONG2018")) {
            ElectionResults result = new ElectionResults(electionDataTable.getRepulican(),
                    electionDataTable.getDemocrat(), electionDataTable.getLibertarian(), electionDataTable.getOther(),
                    ELECTIONS.CONG2018);
            return result;
        } else {
            return null;
        }
    }

    public VotingData getVotingData() {
        HashSet<ElectionResults> data = new HashSet<ElectionResults>();
        for (ElectionDataTable edt : this.electionDataTableSet) {
            data.add(this.convertToElectionResult(edt));
        }
        VotingData vd = new VotingData(data);
        return vd;
    }

    // public String toString() {
    // return "Id : " + Integer.toString(this.id) + " Name : " + this.fullName +
    // "Parent ID : " + this.parentDistrictId
    // + " Errors: " + this.errors + "\nDemographic : " +
    // this.demographic.toString() + "Feature : "
    // + this.feature.toString();
    // }

    // private VotingData votingData;

    // private DemographicData demographicData;

    // private HashSet<Integer> neighborsId;

    // private HashMap<Integer, PrecinctError> precinctErrors;

    // private boolean isGhost;
}