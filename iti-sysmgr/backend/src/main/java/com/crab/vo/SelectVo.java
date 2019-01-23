package com.crab.vo;

import lombok.Data;

@Data
public class SelectVo {
    private String label;
    private Integer value;
    public SelectVo(String label, Integer value){
        this.label = label;
        this.value = value;
    }
}
