package com.crab.shiro.vo;

import java.io.Serializable;
import java.util.List;

/**
 * for all ACL of Module
 * @author YunxiaoXie
 *
 */
public class Acls implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer moduleId;
	/**
	 * 模块名
	 */
	private String moduleName;
	/**
	 * 是否有继承的模块
	 */
	private String moduleExtend;
	/**
	 * 模块访问地址
	 */
	private String moduleUrl;
	
	private List<Power> powers;
	/**
	 * 权限描述
	 * @author YunxiaoXie
	 *
	 */
	public class Power implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 权限名
		 */
		private String powerName;
		/**
		 * 权限sn
		 */
		private Integer powerValue;
		/**
		 * 是否有权限
		 */
		private Boolean powerBool;
		public String getPowerName() {
			return powerName;
		}
		public void setPowerName(String powerName) {
			this.powerName = powerName;
		}
		public Integer getPowerValue() {
			return powerValue;
		}
		public void setPowerValue(Integer powerValue) {
			this.powerValue = powerValue;
		}
		public Boolean getPowerBool() {
			return powerBool;
		}
		public void setPowerBool(Boolean powerBool) {
			this.powerBool = powerBool;
		}
		
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleExtend() {
		return moduleExtend;
	}

	public void setModuleExtend(String moduleExtend) {
		this.moduleExtend = moduleExtend;
	}

	public List<Power> getPowers() {
		return powers;
	}

	public void setPowers(List<Power> powers) {
		this.powers = powers;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	
}
