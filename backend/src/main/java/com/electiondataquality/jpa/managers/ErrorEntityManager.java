package com.electiondataquality.jpa.managers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.tables.ErrorTable;

public class ErrorEntityManager {

    private EntityManagerFactory factory;

    public EntityManager manager;

    public ErrorEntityManager(EntityManagerFactory factory) {
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

    public List<ErrorTable> findAllErrors() {
        List<ErrorTable> results = manager.createQuery("Select a from ErrorTable a", ErrorTable.class).getResultList();

        return results;
    }

    public List<ErrorTable> findErrorByType() {
        List<ErrorTable> results = manager.createQuery("Select a from ErrorTable a where errorType", ErrorTable.class)
                .getResultList();

        return results;
    }
}
