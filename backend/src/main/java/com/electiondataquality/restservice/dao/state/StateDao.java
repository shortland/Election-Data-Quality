package com.electiondataquality.restservice.dao.state;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.electiondataquality.restservice.dao.state.mapper.StateRowMapper;

public class StateDao {
    private DataSource dataSource;

    public void setDataSource(DataSource ds) {
        dataSource = ds;
    }

    // public List<State> select(String firstname, String lastname) {
    // JdbcTemplate select = new JdbcTemplate(dataSource);
    // return select.query("select FIRSTNAME, LASTNAME from PERSON where FIRSTNAME =
    // ? AND LASTNAME= ?",
    // new Object[] { firstname, lastname }, new StateRowMapper());
    // }

    public List<Object> selectAll() {
        JdbcTemplate select = new JdbcTemplate(dataSource);
        return select.query("SELECT states.*, features.* FROM states, features WHERE states.feature_idn = features.idn",
                new StateRowMapper());
    }

}
