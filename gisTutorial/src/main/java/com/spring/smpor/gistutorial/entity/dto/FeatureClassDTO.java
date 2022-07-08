package com.spring.smpor.gistutorial.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swd on 2018/9/6, 导入用, 代表一个图层
 */
@Getter
public class FeatureClassDTO
{
    @JsonIgnore
    private String name;
    private List<FeatureDTO> features;

    public FeatureClassDTO()
    {
        features = new ArrayList<>();
    }

    public void add(FeatureDTO featureDTO)
    {
        features.add(featureDTO);
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
