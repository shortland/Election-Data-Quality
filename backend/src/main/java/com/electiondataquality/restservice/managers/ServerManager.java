package com.electiondataquality.restservice.managers;

import java.util.HashSet;

import com.electiondataquality.restservice.features.state.State;
import com.electiondataquality.restservice.features.congressional_district.CongressionalDistrict;

public class ServerManager {
    private static StateManager stateManager;
    private static CongressionalManager congressionalManager;

    /**
     * Manager Class Constructors
     */

    public ServerManager() {
        ServerManager.stateManager = new StateManager();
    }

    public ServerManager(StateManager sm) {
        ServerManager.stateManager = sm;
    }

    /**
     * StateManager Methods
     */

    public void populateStateManager(HashSet<State> stateSet) {
        ServerManager.stateManager.populate(stateSet);
    }

    public StateManager getStateManager() {
        return ServerManager.stateManager;
    }

    public void setStateManager(StateManager sm) {
        ServerManager.stateManager = sm;
    }

    /**
     * CongressionalDistrictManager Methods
     */
    public void populateCongressionalManager(HashSet<CongressionalDistrict> congressionalSet) {
        ServerManager.congressionalManager.populate(congressionalSet);
    }

    public CongressionalManager getCongressionalManager() {
        return ServerManager.congressionalManager;
    }

    public void setCongressionalManager(CongressionalManager cm) {
        ServerManager.congressionalManager = cm;
    }

    /**
     * PrecinctManager Methods
     */

    /**
     * CommentManager Methods
     */
}