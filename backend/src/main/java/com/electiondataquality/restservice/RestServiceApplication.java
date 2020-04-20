package com.electiondataquality.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.electiondataquality.restservice.managers.ServerManager;

// import java.util.HashSet;
// import java.util.ArrayList;
// import com.electiondataquality.restservice.managers.StateManager;
// import com.electiondataquality.restservice.managers.CongressionalManager;
// import com.electiondataquality.restservice.features.congressional_district.CongressionalDistrict;
// import com.electiondataquality.restservice.features.state.State;

@SpringBootApplication
public class RestServiceApplication {

    /**
     * Create the ServerManager object
     */
    public static ServerManager serverManager;

    public static void main(String[] args) {

        // HashSet<Integer> precincts = new HashSet<Integer>();
        // precincts.add(201);
        // precincts.add(333);
        // ArrayList<ArrayList<double[]>> shape = new ArrayList<ArrayList<double[]>>();
        // double[][][] newShape = { { { 1.2, 1.3 }, { 2.6, 1.4 }, { 3.5, 2.8 } } };
        // for (int i = 0; i < newShape.length; i++) {
        // ArrayList<double[]> midArr = new ArrayList<double[]>();
        // shape.add(midArr);
        // for (int j = 0; j < newShape[i].length; j++) {
        // double[] cord = new double[2];
        // cord[0] = newShape[i][j][0];
        // cord[1] = newShape[i][j][1];
        // shape.get(i).add(cord);
        // }
        // }
        // CongressionalDistrict cd = new CongressionalDistrict("NEWCD", 1, 10,
        // precincts, shape);
        // CongressionalDistrict cd1 = new CongressionalDistrict("CD1", 1, 11,
        // precincts, shape);
        // HashSet<CongressionalDistrict> cdSet = new HashSet<CongressionalDistrict>();
        // cdSet.add(cd);
        // cdSet.add(cd1);
        // CongressionalManager cdmanager = new CongressionalManager(cdSet);

        // HashSet<Integer> cDistrict = new HashSet<Integer>();
        // cDistrict.add(11);
        // cDistrict.add(10);

        // // TEST: stateManager
        // State s = new State(1, "NewYork", "NY", cDistrict, cDistrict, shape);
        // // System.out.println(s.toString());
        // State s1 = new State(110, "Utah", "UT", cDistrict, cDistrict, shape);
        // HashSet<State> stateSet = new HashSet<State>();
        // stateSet.add(s);
        // stateSet.add(s1);
        // StateManager sm = new StateManager(stateSet);

        // HashSet<Integer> congDistrictIds = sm.getAllDistricts(1);

        // ArrayList<CongressionalDistrict> cdList = new ArrayList<>();
        // for (Integer cdId : congDistrictIds) {
        // CongressionalDistrict cddd = cdmanager.getCongDistrict(cdId.intValue());
        // if (cddd != null) {
        // cdList.add(cddd);
        // }
        // }

        // for (CongressionalDistrict cddd : cdList) {
        // System.out.println(cddd.toString());
        // }

        /**
         * Create a server manager object. It's static - thus it's shared amongest the
         * whole application.
         */
        RestServiceApplication.serverManager = new ServerManager();

        /**
         * TODO:
         * 
         * Populate server memory with data hard-coded & locally stored (file: GeoJSON)
         * data. Eventually this should be removed in favor of a database & JPA
         * (persistence layer).
         * 
         * After DB is implemented - keep the DataPopular class around though (commented
         * out or whatever) - it could make testing in dev environment easier in some
         * occasions.
         */
        DataPopulator populator = new DataPopulator(RestServiceApplication.serverManager);
        populator.populateStates();
        populator.populateStates2();
        System.exit(-1);

        /**
         * Run the rest API
         */
        SpringApplication.run(RestServiceApplication.class, args);
    }
}
