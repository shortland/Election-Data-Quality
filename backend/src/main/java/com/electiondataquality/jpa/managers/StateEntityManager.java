package com.electiondataquality.jpa.managers;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.objects.StateFeature;
import com.electiondataquality.jpa.tables.StateTable;

public class StateEntityManager {

    private EntityManagerFactory factory;

    public EntityManager manager;

    public StateEntityManager(EntityManagerFactory factory) {
        this.factory = factory;
        this.manager = factory.createEntityManager();

        this.initialize();
    }

    /**
     * Startup the transactions manager
     */
    private void initialize() {
        this.manager.getTransaction().begin();
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
        this.manager.getTransaction().commit();
        this.manager.close();

        if (cleanFactory) {
            factory.close();
        }
    }

    public List<StateTable> findAllStates() {
        List<StateTable> results = manager.createQuery("Select a from StateTable a", StateTable.class).getResultList();

        return results;
    }

    public Optional<StateTable> findById(int id) {
        StateTable result = manager.createQuery("Select a from StateTable a where state_idn = " + id, StateTable.class)
                .getSingleResult();

        return Optional.of(result);
    }

    public List<StateFeature> findAllStateFeatures() {
        System.out.println("hji");
        List<StateFeature> results = manager.createQuery("Select a from StateFeature a", StateFeature.class)
                .getResultList();

        return results;
    }
}
