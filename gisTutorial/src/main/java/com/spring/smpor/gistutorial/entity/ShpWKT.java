package com.spring.smpor.gistutorial.entity;

public class ShpWKT {

    private String gemoteryWKT;

    public String getGemoteryWKT(){
           return gemoteryWKT;
    }

    public void setGemoteryWKT(String gemoteryWKT) {
        this.gemoteryWKT = gemoteryWKT;
    }

    private String difGeometryWKT;

    public String getDifGeometryWKT() {
        return difGeometryWKT;
    }

    public void setDifGeometryWKT(String difGeometryWKT) {
        this.difGeometryWKT = difGeometryWKT;
    }

    private String featureSet;

    public String getFeatureSet() {
        return featureSet;
    }

    public void setFeatureSet(String featureSet) {
        this.featureSet = featureSet;
    }
}
