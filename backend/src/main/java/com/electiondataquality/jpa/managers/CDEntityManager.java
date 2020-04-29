package com.electiondataquality.jpa.managers;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.objects.CDFeature;

public class CDEntityManager {

    private EntityManagerFactory emf;

    public EntityManager em;

    public CDEntityManager(EntityManagerFactory emf) {
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

    public Optional<CDFeature> findCDFeatureById(int id) {
        CDFeature result = em.createQuery("Select a from CDFeature a where fips_code = " + id, CDFeature.class)
                .getSingleResult();

        return Optional.of(result);
    }

    public Optional<CDFeature> findCDFeatureById(String id) {
        CDFeature result = em.createQuery("Select a from CDFeature a where fips_code = '" + id + "'", CDFeature.class)
                .getSingleResult();

        return Optional.of(result);
    }

    public List<CDFeature> findAllCDFeature() {
        List<CDFeature> results = em.createQuery("Select a from CDFeature a", CDFeature.class).getResultList();

        return results;
    }

    public List<CDFeature> findAllCongressionalDistrictsByStateId(String stateId) {
        List<CDFeature> results = em
                .createQuery("Select a from CDFeature a where state_fips = '" + stateId + "'", CDFeature.class)
                .getResultList();

        return results;
    }
}
