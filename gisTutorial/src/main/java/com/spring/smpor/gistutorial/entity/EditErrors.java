package com.spring.smpor.gistutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by swd on 2018/12/14
 */
public class EditErrors {
    @JsonProperty
    private String ErrorMsg;
    @JsonProperty
    private String GeoRelationship;
    @JsonProperty
    private String CorrelationId;
    @JsonProperty
    private String ErrorCode;
    @JsonIgnore
    public String getErrorMsg() {
        return ErrorMsg;
    }
    @JsonIgnore
    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }
    @JsonIgnore
    public String getGeoRelationship() {
        return GeoRelationship;
    }
    @JsonIgnore
    public void setGeoRelationship(String geoRelationship) {
        GeoRelationship = geoRelationship;
    }
    @JsonIgnore
    public String getCorrelationId() {
        return CorrelationId;
    }
    @JsonIgnore
    public void setCorrelationId(String correlationId) {
        CorrelationId = correlationId;
    }
    @JsonIgnore
    public String getErrorCode() {
        return ErrorCode;
    }
    @JsonIgnore
    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }
}