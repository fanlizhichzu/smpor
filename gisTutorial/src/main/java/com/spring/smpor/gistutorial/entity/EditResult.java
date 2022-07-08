package com.spring.smpor.gistutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swd on 2018/12/14
 */
public class EditResult
{
    @JsonProperty
    private boolean IsSucess;
    @JsonProperty
    private List<EditErrors> EditErrors = new ArrayList<>();
    @JsonProperty
    private String SucceddGuids;
    @JsonProperty
    private  List<String> oids=new ArrayList<>();

    @JsonIgnore
    public boolean isSucess() {
        return IsSucess;
    }
    @JsonIgnore
    public void setSucess(boolean sucess) {
        IsSucess = sucess;
    }
    @JsonIgnore
    public List<EditErrors> getEditErrors() {
        return EditErrors;
    }
    @JsonIgnore
    public void setEditErrors(List<EditErrors> editErrors) {
        EditErrors = editErrors;
    }
    @JsonIgnore
    public String getSucceddGuids() {
        return SucceddGuids;
    }
    @JsonIgnore
    public void setSucceddGuids(String succeddGuids) {
        SucceddGuids = succeddGuids;
    }
    @JsonIgnore
    public List<String> getOids() {
        return oids;
    }
    @JsonIgnore
    public void setOids(List<String> oids) {
        this.oids = oids;
    }
}
