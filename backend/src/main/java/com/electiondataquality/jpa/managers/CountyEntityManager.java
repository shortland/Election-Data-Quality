package com.electiondataquality.jpa.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.tables.CountyTable;

public class CountyEntityManager {

    private EntityManagerFactory factory;

    public EntityManager manager;

    public CountyEntityManager(EntityManagerFactory factory) {
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

    public List<CountyTable> findCountiesByState(String stateId) {
        List<CountyTable> results = manager
                .createQuery("Select a from CountyTable a where state_idn = " + stateId, CountyTable.class)
                .getResultList();

        return results;
    }
}