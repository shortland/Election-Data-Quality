package com.electiondataquality.features.util;

import java.util.List;

import com.electiondataquality.features.Feature;

public class FeatureWrap {
    public static FeatureCollectionWrapper wrap(List<Feature> features) {
        FeatureCollectionWrapper wrapper = new FeatureCollectionWrapper();

        wrapper.setFeatures(features);

        return wrapper;
    }
}
