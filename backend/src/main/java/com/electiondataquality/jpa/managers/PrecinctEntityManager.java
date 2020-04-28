package com.electiondataquality.jpa.managers;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.objects.PrecinctFeature;

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
}
