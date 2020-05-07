package com.electiondataquality.jpa.managers;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.DemographicTable;
import com.electiondataquality.jpa.tables.ElectionDataTable;
import com.electiondataquality.jpa.tables.ErrorTable;
import com.electiondataquality.jpa.tables.FeatureTable;
import com.electiondataquality.restservice.voting.elections.enums.ELECTIONS;

public class PrecinctEntityManager {

    private EntityManagerFactory emf;

    public EntityManager em;

    public PrecinctEntityManager(EntityManagerFactory emf) {
        this.emf = emf;
        this.em = emf.createEntityManager();

        this.initialize();
    }

    /**
     * Startup the transactions manager
     */
    private void initialize() {
        this.em.getTransaction().begin();
    }

    /**
     * Close managers
     */
    public void cleanup() {
        this.cleanup(false);
    }

    /**
     * Cleanup the entity manager factory
     */
    public void cleanup(boolean cleanFactory) {
        this.em.getTransaction().commit();
        this.em.close();

        if (cleanFactory) {
            emf.close();
        }
    }

    public Optional<PrecinctFeature> findPrecinctFeatureById(String id) {
        PrecinctFeature result = em
                .createQuery("Select a from PrecinctFeature a where precinct_idn = '" + id + "'", PrecinctFeature.class)
                .getSingleResult();

        return Optional.of(result);
    }

    public List<PrecinctFeature> findAllPrecinctFeature() {
        List<PrecinctFeature> results = em.createQuery("Select a from PrecinctFeature a", PrecinctFeature.class)
                .getResultList();

        return results;
    }

    public List<PrecinctFeature> findAllPrecinctFeaturesByCountyId(String countyId) {
        List<PrecinctFeature> results = em
                .createQuery("Select a from PrecinctFeature a where county_fips_code = '" + countyId + "'",
                        PrecinctFeature.class)
                .getResultList();

        return results;
    }

    public Optional<FeatureTable> findFeatureByFeatureId(int featureIdn) {
        FeatureTable result = em
                .createQuery("Select a from FeatureTable a where idn = " + featureIdn, FeatureTable.class)
                .getSingleResult();

        return Optional.of(result);
    }

    public Optional<ErrorTable> findErrorsByPrecinctId(int errorId) {
        ErrorTable result = em.createQuery("Select a from ErrorTable a where idn = " + errorId, ErrorTable.class)
                .getSingleResult();

        return Optional.of(result);
    }

    public Optional<DemographicTable> findDemographicByPrecinctId(String precinct_idn) {
        DemographicTable result = em
                .createQuery("Select a from DemographicTable a where precinct_idn = '" + precinct_idn + "'",
                        DemographicTable.class)
                .getSingleResult();

        return Optional.of(result);
    }

    public Optional<ElectionDataTable> findElectionDataByPrecinctId(String precinct_idn, ELECTIONS election) {
        ElectionDataTable result = em.createQuery("Select a from ElectionDataTable a where precinct_idn = '"
                + precinct_idn + "' and election = '" + election + "'", ElectionDataTable.class).getSingleResult();

        return Optional.of(result);
    }

    public void removePrecinct(PrecinctFeature precinctFeature) {
        em.remove(precinctFeature);
    }

    public void removeError(ErrorTable errorTable) {
        em.remove(errorTable);
    }

    public void persistPrecinct(PrecinctFeature precinctFeature) {
        em.persist(precinctFeature);
    }

    public void persistError(ErrorTable errorTable) {
        em.persist(errorTable);
    }

    public void persistDemographic(DemographicTable demographicTable) {
        em.persist(demographicTable);
    }

    public void persistElectionData(ElectionDataTable electionDataTable) {
        em.persist(electionDataTable);
    }

}
