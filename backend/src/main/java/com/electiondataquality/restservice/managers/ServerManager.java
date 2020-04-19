package com.electiondataquality.restservice.managers;

import java.util.HashSet;

import com.electiondataquality.restservice.features.state.State;

public class ServerManager {
    private static StateManager stateManager;

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
     * PrecinctManager Methods
     */

    /**
     * CongressionalDistrictManager Methods
     */

    /**
     * CommentManager Methods
     */
}