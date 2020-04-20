package com.electiondataquality.restservice.dao.state.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.electiondataquality.restservice.features.state.State;

public class StateResultSetExtractor implements ResultSetExtractor<Object> {
    @Override
    public Object extractData(ResultSet rs) throws SQLException {
        // State(int stateId, String stateName, String stateAbreviation,
        // HashSet<Integer> counties, HashSet<Integer> districts,
        // ArrayList<ArrayList<double[]>> shape)
        // rs.getString(7)
        State state = new State(rs.getInt(3), rs.getString(2), rs.getString(1), null, null, null);
        return state;
    }
}
