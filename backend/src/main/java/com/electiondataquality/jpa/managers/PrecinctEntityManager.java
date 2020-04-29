package com.electiondataquality.jpa.managers;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.objects.PrecinctFeature;
import com.electiondataquality.jpa.tables.ElectionDataTable;
import com.electiondataquality.jpa.tables.ErrorTable;

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

    public PrecinctFeature findPrecinctFeatureById(String id) {
        PrecinctFeature result = em
                .createQuery("Select a from PrecinctFeature a where precinct_idn = '" + id + "'", PrecinctFeature.class)
                .getSingleResult();

        return result;
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

    public void removePrecinct(PrecinctFeature precinctFeature) {
        em.remove(precinctFeature);
    }

    public void persistPrecinct(PrecinctFeature precinctFeature) {
        em.persist(precinctFeature);
    }

    public void persistErrorTable(ErrorTable errorTable) {
        em.persist(errorTable);
    }

    public void persistElectionDataTable(ElectionDataTable electionDataTable) {
        em.persist(electionDataTable);
    }

}
