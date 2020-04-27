package com.electiondataquality.jpa.managers;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.tables.StateTable;

public class StateTableEntityManager {
    private EntityManagerFactory factory;

    public EntityManager manager;

    public StateTableEntityManager(EntityManagerFactory factory) {
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

    public List<StateTable> findAll() {
        List<StateTable> results = manager.createQuery("Select a from StateTable a", StateTable.class).getResultList();

        return results;
    }

    public Optional<StateTable> findById(int id) {
        StateTable result = manager.createQuery("Select a from StateTable a where state_idn = " + id, StateTable.class)
                .getSingleResult();

        return Optional.of(result);
    }
}
