package com.electiondataquality.features.util;

import java.util.List;

import com.electiondataquality.features.Feature;

public class CompareFeatureShape {
    public static boolean CompareFeatures(Feature f1, Feature f2) {
        List<List<List<double[]>>> shape1 = f1.getShape();
        List<List<List<double[]>>> shape2 = f2.getShape();

        if (shape1.size() == shape2.size()) {
            for (int i = 0; i < shape1.size(); i++) {
                List<List<double[]>> secondLayer1 = shape1.get(i);
                List<List<double[]>> secondLayer2 = shape2.get(i);

                if (secondLayer1.size() == secondLayer2.size()) {
                    for (int j = 0; j < secondLayer1.size(); j++) {
                        List<double[]> thirdLayer1 = secondLayer1.get(j);
                        List<double[]> thirdLayer2 = secondLayer2.get(j);

                        if (thirdLayer1.size() == thirdLayer2.size()) {
                            for (int k = 0; k < thirdLayer1.size(); k++) {
                                boolean xSame = false;
                                boolean ySame = false;

                                double x1 = thirdLayer1.get(k)[0];
                                double x2 = thirdLayer2.get(k)[0];
                                double y1 = thirdLayer1.get(k)[1];
                                double y2 = thirdLayer2.get(k)[1];

                                if (Double.compare(x1, x2) == 0) {
                                    xSame = true;
                                }

                                if (Double.compare(y1, y2) == 0) {
                                    ySame = true;
                                }

                                if (!(xSame || ySame)) {
                                    return false;
                                }
                            }
                        } else {
                            return false;
                        }
                    }
                } else {
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
