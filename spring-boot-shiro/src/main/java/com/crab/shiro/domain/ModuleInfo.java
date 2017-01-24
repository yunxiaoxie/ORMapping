package com.crab.shiro.domain;

import java.io.Serializable;
import java.util.Date;

public class ModuleInfo implements Serializable{
	private static final long serialVersionUID = -4297656027873404254L;

	private int id;
	private int parent_id;
	private String module_name;
	private String module_url;
	private int order_no;
	private String module_sn;
	private Date create_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public String getModule_name() {
		return module_name;
	}
	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}
	public String getModule_url() {
		return module_url;
	}
	public void setModule_url(String module_url) {
		this.module_url = module_url;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public String getModule_sn() {
		return module_sn;
	}
	public void setModule_sn(String module_sn) {
		this.module_sn = module_sn;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}