package com.crab.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModuleInfo implements Serializable{
    private static final long serialVersionUID = -4297656027873404254L;

    private int id;
    private int parent_id;
    private String module_name;
    private String module_url;
    private int order_no;
    private String module_sn;
    private Date create_time;

}