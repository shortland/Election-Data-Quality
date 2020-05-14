package com.electiondataquality.features.util;

import java.util.List;

import com.electiondataquality.features.Feature;

public class FeatureCollectionWrapper {

    private String type = "FeatureCollection";

    private List<Feature> features;

    public FeatureCollectionWrapper(String type, List<Feature> features) {
        this.type = type;
        this.features = features;
    }

    public FeatureCollectionWrapper(List<Feature> features) {
        this.features = features;
    }

    public FeatureCollectionWrapper() {
    }

    public String getType() {
        return type;
    }

    public String setType(String type) {
        return this.type = type;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
