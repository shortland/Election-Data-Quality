package com.electiondataquality.restservice.features;

import java.util.ArrayList;

public abstract class Feature {
    private ArrayList<ArrayList<double[]>> shape;

    public Feature() {
        this.shape = new ArrayList<ArrayList<double[]>>();
    }

    public Feature(ArrayList<ArrayList<double[]>> shape) {
        this.shape = shape;
    }

    public ArrayList<ArrayList<double[]>> getShape() {
        return this.shape;
    }

    public void setShape(ArrayList<ArrayList<double[]>> newShape) {
        this.shape = newShape;
    }

    // double[][][2]
    public void setShape(double[][][] newShape) {
        this.shape.clear();
        // create arrayList<double[]>
        for (int i = 0; i < newShape.length; i++) {
            ArrayList<double[]> midArr = new ArrayList<double[]>();
            this.shape.add(midArr);
            for (int j = 0; j < newShape[i].length; j++) {
                double[] cord = new double[2];
                cord[0] = newShape[i][j][0];
                cord[1] = newShape[i][j][1];
                this.shape.get(i).add(cord);
            }
        }
    }

    public String shapeToString() {
        String str = "";
        for (int i = 0; i < this.shape.size(); i++) {
            for (int j = 0; j < this.shape.get(i).size(); j++) {
                str = str + Double.toString(this.shape.get(i).get(j)[0]) + ", "
                        + Double.toString(this.shape.get(i).get(j)[1]) + "\n";
            }
        }

        return str;
    }
}