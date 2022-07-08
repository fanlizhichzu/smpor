package com.spring.smpor.gistutorial.entity.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by swd on 2018/9/6, 导入用, 代表一个要素
 */
@Data
public class FeatureDTO
{
    private String wkt;
    private Map<String, String> attributes;

    public FeatureDTO()
    {
        attributes = new HashMap<>();
    }

    public void addAttribute(String key, String value)
    {
        attributes.put(key, value);
    }

}
