package com.electiondataquality.geometry;

import java.util.List;

public class Geometry {

    public String type;

    public List<List<List<double[]>>> coordinates;

    public String rawCoordsAsString() {
        String s = "";

        for (List<List<double[]>> l1 : coordinates) {
            s += "[";

            for (List<double[]> l2 : l1) {
                s += "[";

                for (double[] d1 : l2) {
                    s += "[" + d1[0] + "," + d1[1] + "]";
                }

                s += "]";
            }

            s += "]";
        }

        s = s.replaceAll("\\]\\[", "\\],\\[");

        return s;
    }

    @Override
    public String toString() {
        return "Type: " + this.type + ", Coordinates: " + this.rawCoordsAsString();
    }
}
