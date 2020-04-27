package com.electiondataquality.dao.state.mapper;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.ResultSetExtractor;
import com.google.gson.Gson;

import com.electiondataquality.features.state.State;
import com.electiondataquality.geometry.GeometryType;
import com.electiondataquality.geometry.MultiPolygon;
import com.electiondataquality.geometry.Polygon;

public class StateResultSetExtractor implements ResultSetExtractor<Object> {

    @Override
    public Object extractData(ResultSet rs) throws SQLException {
        Gson gson = new Gson();

        GeometryType geoType = gson.fromJson(rs.getString(7), GeometryType.class);
        if ((geoType.type).equals("Polygon")) {
            Polygon polygon = gson.fromJson(rs.getString(7), Polygon.class);

            return new State(rs.getInt(3), rs.getString(2), rs.getString(1), null, null, polygon);
        }

        if ((geoType.type).equals("MultiPolygon")) {
            MultiPolygon multiPolygon = gson.fromJson(rs.getString(7), MultiPolygon.class);

            return new State(rs.getInt(3), rs.getString(2), rs.getString(1), null, null, multiPolygon);
        }

        return new State(rs.getInt(3), rs.getString(2), rs.getString(1), null, null,
                new Polygon(new ArrayList<ArrayList<double[]>>()).toMultiPolygon());
    }
}
