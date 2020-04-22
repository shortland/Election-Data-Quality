package com.electiondataquality.features;

import com.electiondataquality.geometry.MultiPolygon;

public interface Shape {
    public void setShape(MultiPolygon polygon);

    public MultiPolygon getShape();
}
