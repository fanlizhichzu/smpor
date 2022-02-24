package com.spring.smpor.common.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Description:
 * @Auther: fanlz
 * @Date: 2021/12/21 10:32
 */
@Getter
@Setter
public class PageEntity {
    private int current;
    private int size;
    private Long total;
    private List<?> data;
    private String orderSql = "";
}
