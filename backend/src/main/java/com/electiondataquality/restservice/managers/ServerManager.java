package com.electiondataquality.restservice.managers;

import java.util.HashSet;

import com.electiondataquality.features.state.State;
import com.electiondataquality.features.congressional_district.CongressionalDistrict;
import com.electiondataquality.features.precinct.Precinct;
import com.electiondataquality.restservice.comments.Comment;

public class ServerManager {

    private static StateManager stateManager;

    private static CongressionalManager congressionalManager;

    private static PrecinctManager precinctManager;

    private static CommentManager commentManager;

    public ServerManager() {
        ServerManager.stateManager = new StateManager();
        ServerManager.congressionalManager = new CongressionalManager();
        ServerManager.precinctManager = new PrecinctManager();
        ServerManager.commentManager = new CommentManager();
    }

    public ServerManager(StateManager sm, CongressionalManager cgm, PrecinctManager pm, CommentManager cm) {
        ServerManager.stateManager = sm;
        ServerManager.congressionalManager = cgm;
        ServerManager.precinctManager = pm;
        ServerManager.commentManager = cm;
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
    public void populatePrecinctManager(HashSet<Precinct> precinctSet) {
        ServerManager.precinctManager.populate(precinctSet);
    }

    public PrecinctManager getPrecinctManager() {
        return ServerManager.precinctManager;
    }

    public void setPrecinctManager(PrecinctManager pm) {
        ServerManager.precinctManager = pm;
    }

    /**
     * CommentManager Methods
     */
    public void populateCommentManager(HashSet<Comment> commentSet) {
        ServerManager.commentManager.populate(commentSet);
    }

    public CommentManager getCommentManager() {
        return ServerManager.commentManager;
    }

    public void setCommentManager(CommentManager cm) {
        ServerManager.commentManager = cm;
    }
}
