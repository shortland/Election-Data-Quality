package manager;

import java.util.HashSet;
//import self created package
import feature.*;
import comment.*;

public class ServerManager {
    private PrecinctManager precinctManager;
    private StateManager stateManager;
    private CongDistrictManager cdManager;
    private CommentManager commentManager;

    // Deafualt constructor
    public ServerManager() {
        this.precinctManager = new PrecinctManager();
        this.cdManager = new CongDistrictManager();
        this.stateManager = new StateManager();
        this.commentManager = new CommentManagerg();
    }

    public ServerManager(PrecinctManager pm, CongDistrictManager cdm, StateManager sm, CommentManager cm) {
        this.precinctManager = pm;
        this.cdManager = cdm;
        this.stateManager = sm;
        this.commentManager = cm;
    }

    public void populateStateManager(HashSet<State> stateSet) {
        this.stateManager.populate(stateSet);
    }

    public void populateCongDistrictManager(HashSet<CongressionalDistrict> cdSet) {
        this.cdManager.populate(cdSet);
    }

    public void populatePrecinctManager(HashSet<Precinct> precinctSet) {
        this.precinctManager.populate(precinctSet);
    }

    public void populateCommentManager(HashSet<Comment> commentSet) {
        this.commentManager.populate(commentSet);
    }

    public PrecinctManager getPrecinctManager() {
        return this.precinctManager;
    }

    public CongDistrictManager getCDManager() {
        return this.cdManager;
    }

    public StateManager getStateManager() {
        return this.stateManager;
    }

    public CommentManager getCommentManager() {
        return this.commentManager;
    }

    public void setStateManager(StateManager sm) {
        this.stateManager = sm;
    }

    public void setCongDistrictManager(CongDistrictManager cdm) {
        this.cdManager = cdm;
    }

    public void setPrecinctManager(PrecinctManager pm) {
        this.precinctManager = pm;
    }

    public void setCommentManager(CommentManager cm) {
        this.commentManager = cm;
    }
}