package com.electiondataquality.jpa.managers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.tables.ErrorTable;

public class ErrorEntityManager {

    private EntityManagerFactory emf;

    public EntityManager em;

    public ErrorEntityManager(EntityManagerFactory emf) {
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

    public List<ErrorTable> findAllErrors() {
        List<ErrorTable> results = em.createQuery("Select a from ErrorTable a", ErrorTable.class).getResultList();

        return results;
    }
}
