package com.iti.rediscluster.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Student implements Serializable {
    private String name;
    private String sex;
    private Integer age;
    private String address;
}
