package com.crab.redis.domain;

import java.io.Serializable;
import java.util.List;

public class SysDataDic implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer pid;
	private String dic_key;
	private String dic_value;
	private Integer is_stope;
	
	private List<SysDataDic> child;
	
	public SysDataDic() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getDic_key() {
		return dic_key;
	}

	public void setDic_key(String dic_key) {
		this.dic_key = dic_key;
	}

	public String getDic_value() {
		return dic_value;
	}

	public void setDic_value(String dic_value) {
		this.dic_value = dic_value;
	}

	public Integer getIs_stope() {
		return is_stope;
	}

	public void setIs_stope(Integer is_stope) {
		this.is_stope = is_stope;
	}

	public List<SysDataDic> getChild() {
		return child;
	}

	public void setChild(List<SysDataDic> child) {
		this.child = child;
	}
}
