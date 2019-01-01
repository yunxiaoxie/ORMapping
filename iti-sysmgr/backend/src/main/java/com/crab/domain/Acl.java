package com.crab.domain;

import java.io.Serializable;
import java.util.Date;

public class Acl implements Serializable {
    private Integer id;

    private String principalType;

    private Integer principalSn;

    private Integer resourceSn;

    private Integer aclState;

    private Integer aclExtState;

    private Date createTime;

    private static final long serialVersionUID = 1L;

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