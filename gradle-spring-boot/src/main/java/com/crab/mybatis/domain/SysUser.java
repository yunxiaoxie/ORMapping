package com.crab.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

public class SysUser implements Serializable {
    /**
     *
     *  主键, 所属表字段为sys_user.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     *  账号, 所属表字段为sys_user.user
     *
     * @mbg.generated
     */
    private String user;

    /**
     *
     *  密码, 所属表字段为sys_user.pwd
     *
     * @mbg.generated
     */
    private String pwd;

    /**
     *
     *  是否停用, 所属表字段为sys_user.is_stop
     *
     * @mbg.generated
     */
    private String isStop;

    /**
     *
     *  停用时间, 所属表字段为sys_user.stop_time
     *
     * @mbg.generated
     */
    private Date stopTime;

    /**
     *
     *  停用人, 所属表字段为sys_user.stop_user
     *
     * @mbg.generated
     */
    private String stopUser;

    /**
     *
     *  创建时间, 所属表字段为sys_user.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     *  创建人, 所属表字段为sys_user.create_user
     *
     * @mbg.generated
     */
    private String createUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user == null ? null : user.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getIsStop() {
        return isStop;
    }

    public void setIsStop(String isStop) {
        this.isStop = isStop == null ? null : isStop.trim();
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getStopUser() {
        return stopUser;
    }

    public void setStopUser(String stopUser) {
        this.stopUser = stopUser == null ? null : stopUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
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
        SysUser other = (SysUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUser() == null ? other.getUser() == null : this.getUser().equals(other.getUser()))
            && (this.getPwd() == null ? other.getPwd() == null : this.getPwd().equals(other.getPwd()))
            && (this.getIsStop() == null ? other.getIsStop() == null : this.getIsStop().equals(other.getIsStop()))
            && (this.getStopTime() == null ? other.getStopTime() == null : this.getStopTime().equals(other.getStopTime()))
            && (this.getStopUser() == null ? other.getStopUser() == null : this.getStopUser().equals(other.getStopUser()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
        result = prime * result + ((getPwd() == null) ? 0 : getPwd().hashCode());
        result = prime * result + ((getIsStop() == null) ? 0 : getIsStop().hashCode());
        result = prime * result + ((getStopTime() == null) ? 0 : getStopTime().hashCode());
        result = prime * result + ((getStopUser() == null) ? 0 : getStopUser().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", pwd=").append(pwd);
        sb.append(", isStop=").append(isStop);
        sb.append(", stopTime=").append(stopTime);
        sb.append(", stopUser=").append(stopUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}