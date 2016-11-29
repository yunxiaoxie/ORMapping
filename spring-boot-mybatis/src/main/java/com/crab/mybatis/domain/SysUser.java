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
    private String is_stop;

    /**
     *
     *  停用时间, 所属表字段为sys_user.stop_time
     *
     * @mbg.generated
     */
    private Date stop_time;

    /**
     *
     *  停用人, 所属表字段为sys_user.stop_user
     *
     * @mbg.generated
     */
    private String stop_user;

    /**
     *
     *  创建时间, 所属表字段为sys_user.create_time
     *
     * @mbg.generated
     */
    private Date create_time;

    /**
     *
     *  创建人, 所属表字段为sys_user.create_user
     *
     * @mbg.generated
     */
    private String create_user;

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

    public String getIs_stop() {
        return is_stop;
    }

    public void setIs_stop(String is_stop) {
        this.is_stop = is_stop == null ? null : is_stop.trim();
    }

    public Date getStop_time() {
        return stop_time;
    }

    public void setStop_time(Date stop_time) {
        this.stop_time = stop_time;
    }

    public String getStop_user() {
        return stop_user;
    }

    public void setStop_user(String stop_user) {
        this.stop_user = stop_user == null ? null : stop_user.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user == null ? null : create_user.trim();
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
            && (this.getIs_stop() == null ? other.getIs_stop() == null : this.getIs_stop().equals(other.getIs_stop()))
            && (this.getStop_time() == null ? other.getStop_time() == null : this.getStop_time().equals(other.getStop_time()))
            && (this.getStop_user() == null ? other.getStop_user() == null : this.getStop_user().equals(other.getStop_user()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getCreate_user() == null ? other.getCreate_user() == null : this.getCreate_user().equals(other.getCreate_user()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUser() == null) ? 0 : getUser().hashCode());
        result = prime * result + ((getPwd() == null) ? 0 : getPwd().hashCode());
        result = prime * result + ((getIs_stop() == null) ? 0 : getIs_stop().hashCode());
        result = prime * result + ((getStop_time() == null) ? 0 : getStop_time().hashCode());
        result = prime * result + ((getStop_user() == null) ? 0 : getStop_user().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getCreate_user() == null) ? 0 : getCreate_user().hashCode());
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
        sb.append(", is_stop=").append(is_stop);
        sb.append(", stop_time=").append(stop_time);
        sb.append(", stop_user=").append(stop_user);
        sb.append(", create_time=").append(create_time);
        sb.append(", create_user=").append(create_user);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}