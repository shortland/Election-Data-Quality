package com.electiondataquality.dao.state.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StateRowMapper implements RowMapper<Object> {

    @Override
    public Object mapRow(ResultSet rs, int line) throws SQLException {
        StateResultSetExtractor extractor = new StateResultSetExtractor();

        return extractor.extractData(rs);
    }
}
