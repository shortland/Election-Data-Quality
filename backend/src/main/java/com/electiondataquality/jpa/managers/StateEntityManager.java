package com.electiondataquality.jpa.managers;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.electiondataquality.jpa.objects.StateFeature;
import com.electiondataquality.jpa.tables.CongressionalDistrictTable;
import com.electiondataquality.jpa.tables.CountyTable;
import com.electiondataquality.jpa.tables.StateTable;

public class StateEntityManager {

    private EntityManagerFactory stateFactory;

    private EntityManagerFactory countyFactory;

    private EntityManagerFactory congrFactory;

    public EntityManager stateManager;

    public EntityManager countyManager;

    public EntityManager congrManager;

    public StateEntityManager(EntityManagerFactory factory) {
        this.stateFactory = factory;
        this.stateManager = factory.createEntityManager();

        this.initialize();
    }

    public StateEntityManager(EntityManagerFactory stateFactory, EntityManagerFactory countyFactory) {
        this.stateFactory = stateFactory;
        this.stateManager = stateFactory.createEntityManager();

        this.countyFactory = countyFactory;
        this.countyManager = countyFactory.createEntityManager();

        this.stateManager.getTransaction().begin();
        this.countyManager.getTransaction().begin();
    }

    public StateEntityManager(EntityManagerFactory stateFactory, EntityManagerFactory countyFactory,
            EntityManagerFactory congrFactory) {
        this.stateFactory = stateFactory;
        this.stateManager = stateFactory.createEntityManager();

        this.countyFactory = countyFactory;
        this.countyManager = countyFactory.createEntityManager();

        this.congrFactory = congrFactory;
        this.congrManager = congrFactory.createEntityManager();

        this.stateManager.getTransaction().begin();
        this.countyManager.getTransaction().begin();
        this.congrManager.getTransaction().begin();
    }

    /**
     * Startup the transactions manager
     */
    private void initialize() {
        this.stateManager.getTransaction().begin();
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
        this.stateManager.getTransaction().commit();
        this.stateManager.close();

        if (cleanFactory) {
            this.stateFactory.close();
        }

        if (countyManager != null) {
            this.countyManager.getTransaction().commit();
            this.countyManager.close();

            if (cleanFactory) {
                this.countyFactory.close();
            }
        }

        if (congrManager != null) {
            this.congrManager.getTransaction().commit();
            this.congrManager.close();

            if (cleanFactory) {
                this.congrFactory.close();
            }
        }
    }

    public List<StateTable> findAllStates() {
        List<StateTable> results = this.stateManager.createQuery("Select a from StateTable a", StateTable.class)
                .getResultList();

        return results;
    }

    public StateTable findById(String id) {
        StateTable result = this.stateManager
                .createQuery("Select a from StateTable a where state_idn = '" + id + "'", StateTable.class)
                .getSingleResult();

        return result;
    }

    public List<StateFeature> findAllStateFeatures() {
        List<StateFeature> results = this.stateManager.createQuery("Select a from StateFeature a", StateFeature.class)
                .getResultList();

        return results;
    }

    public List<CountyTable> findAllCountiesByStateId(String stateId) {
        List<CountyTable> results = this.countyManager
                .createQuery("Select a from CountyTable a where state_idn = '" + stateId + "'", CountyTable.class)
                .getResultList();

        return results;
    }

    public List<CongressionalDistrictTable> findAllCongressionalDistrictsByStateId(String stateId) {
        List<CongressionalDistrictTable> results = this.congrManager
                .createQuery("Select a from CongressionalDistrictTable a where state_fips = '" + stateId + "'",
                        CongressionalDistrictTable.class)
                .getResultList();

        return results;
    }
}
