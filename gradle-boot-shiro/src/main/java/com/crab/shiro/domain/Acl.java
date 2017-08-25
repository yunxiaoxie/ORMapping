package com.crab.shiro.domain;

import java.io.Serializable;
import java.util.Date;

public class Acl implements Serializable {
	
	/*
	 * 主体类型：角色
	 */
	public static final String TYPE_ROLE = "ROLE";
	
	/*
	 * 主体类型：用户
	 */
	public static final String TYPE_USER = "USER";
	/*
	 * 授权允许
	 */
	public static final int ACL_YES = 1;

	/*
	 * 授权拒绝
	 */
	public static final int ACL_NO = 0;

	/*
	 * 授权不确定
	 */
	public static final int ACL_NEUTRAL = -1;
	
	/*
	 * 继承状态
	 */
	public static final int ACL_EXTENDS = -1;
	/*
	 * 不继承状态
	 */
	public static final int ACL_NOTEXTENDS = 0;
	
    /**
     *
     *  , 所属表字段为p_acl.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     *  主体类型(角色或用户), 所属表字段为p_acl.principal_type
     *
     * @mbg.generated
     */
    private String principalType;

    /**
     *
     *  主体标识(ID), 所属表字段为p_acl.principal_sn
     *
     * @mbg.generated
     */
    private Integer principalSn;

    /**
     *
     *  资源标识(Module ID), 所属表字段为p_acl.resource_sn
     *
     * @mbg.generated
     */
    private Integer resourceSn;

    /**
     *
     *  授权状态(用（bit）表示, 64bit-20位max), 所属表字段为p_acl.acl_state
     *
     * @mbg.generated
     */
    private Integer aclState;

    /**
     *
     *  是否继承, 所属表字段为p_acl.acl_ext_state
     *
     * @mbg.generated
     */
    private Integer aclExtState;

    /**
     *
     *  , 所属表字段为p_acl.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;
    
    /**
	 * 获得ACL授权,判断是否有READ权限
	 * @param permission C/R/U/D权限
	 * @return 授权标识：允许/不允许/不确定
	 */
	public int getPermission(int permission) {

		if(aclExtState == 0xFFFFFFFF){
			return ACL_NEUTRAL;
		}
		
		int tmp = 1;
		
		tmp = tmp << permission;
		
		tmp &= aclState;
		
		if(tmp != 0){
			return ACL_YES;
		}
		
		return ACL_NO;
	}

	/**
	 * acl实例跟主体和资源关联
	 * 针对此实例进行授权：某种操作是否允许
	 * @param permission 只可以取值0,1,2,3
	 * @param yes true表示允许，false表示不允许
	 */
	public void setPermission(int permission, boolean yes) {
		int tmp = 1;
		tmp = tmp << permission;
		if(yes){
			aclState |= tmp;
		}else{
			aclState &= ~tmp;
		}
	}
	
	/**
	 * 设置本授权是否是继承的(继承--角色授权,不继承--用户授权)
	 * 只有给用户的授权(不继承)才会被读取
	 * @param yes true表示继承，false表示不继承
	 */
	public void setExtends(boolean yes){
		if(yes){
			aclExtState = 0xFFFFFFFF;
		}else{
			aclExtState = 0;
		}
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrincipalType() {
        return principalType;
    }

    public void setPrincipalType(String principalType) {
        this.principalType = principalType == null ? null : principalType.trim();
    }

    public Integer getPrincipalSn() {
        return principalSn;
    }

    public void setPrincipalSn(Integer principalSn) {
        this.principalSn = principalSn;
    }

    public Integer getResourceSn() {
        return resourceSn;
    }

    public void setResourceSn(Integer resourceSn) {
        this.resourceSn = resourceSn;
    }

    public Integer getAclState() {
        return aclState;
    }

    public void setAclState(Integer aclState) {
        this.aclState = aclState;
    }

    public Integer getAclExtState() {
        return aclExtState;
    }

    public void setAclExtState(Integer aclExtState) {
        this.aclExtState = aclExtState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Acl other = (Acl) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPrincipalType() == null ? other.getPrincipalType() == null : this.getPrincipalType().equals(other.getPrincipalType()))
            && (this.getPrincipalSn() == null ? other.getPrincipalSn() == null : this.getPrincipalSn().equals(other.getPrincipalSn()))
            && (this.getResourceSn() == null ? other.getResourceSn() == null : this.getResourceSn().equals(other.getResourceSn()))
            && (this.getAclState() == null ? other.getAclState() == null : this.getAclState().equals(other.getAclState()))
            && (this.getAclExtState() == null ? other.getAclExtState() == null : this.getAclExtState().equals(other.getAclExtState()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPrincipalType() == null) ? 0 : getPrincipalType().hashCode());
        result = prime * result + ((getPrincipalSn() == null) ? 0 : getPrincipalSn().hashCode());
        result = prime * result + ((getResourceSn() == null) ? 0 : getResourceSn().hashCode());
        result = prime * result + ((getAclState() == null) ? 0 : getAclState().hashCode());
        result = prime * result + ((getAclExtState() == null) ? 0 : getAclExtState().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", principalType=").append(principalType);
        sb.append(", principalSn=").append(principalSn);
        sb.append(", resourceSn=").append(resourceSn);
        sb.append(", aclState=").append(aclState);
        sb.append(", aclExtState=").append(aclExtState);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}