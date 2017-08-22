package com.crab.mybatis.domain;

import java.io.Serializable;

public class SysDataDic implements Serializable {
    /**
     *
     *  主键, 所属表字段为sys_data_dic.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     *  父主键, 所属表字段为sys_data_dic.pid
     *
     * @mbg.generated
     */
    private Integer pid;

    /**
     *
     *  页面通过它取值, 所属表字段为sys_data_dic.code
     *
     * @mbg.generated
     */
    private Integer code;

    /**
     *
     *  value, 所属表字段为sys_data_dic.value
     *
     * @mbg.generated
     */
    private String value;

    /**
     *
     *  text, 所属表字段为sys_data_dic.text
     *
     * @mbg.generated
     */
    private String text;

    /**
     *
     *  是否停用(-1-停用, 空或0-启用), 所属表字段为sys_data_dic.is_stop
     *
     * @mbg.generated
     */
    private Integer isStop;

    /**
     *
     *  注释, 所属表字段为sys_data_dic.comet
     *
     * @mbg.generated
     */
    private String comet;

    private static final long serialVersionUID = 1L;

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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Integer getIsStop() {
        return isStop;
    }

    public void setIsStop(Integer isStop) {
        this.isStop = isStop;
    }

    public String getComet() {
        return comet;
    }

    public void setComet(String comet) {
        this.comet = comet == null ? null : comet.trim();
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
        SysDataDic other = (SysDataDic) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
            && (this.getText() == null ? other.getText() == null : this.getText().equals(other.getText()))
            && (this.getIsStop() == null ? other.getIsStop() == null : this.getIsStop().equals(other.getIsStop()))
            && (this.getComet() == null ? other.getComet() == null : this.getComet().equals(other.getComet()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
        result = prime * result + ((getText() == null) ? 0 : getText().hashCode());
        result = prime * result + ((getIsStop() == null) ? 0 : getIsStop().hashCode());
        result = prime * result + ((getComet() == null) ? 0 : getComet().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", code=").append(code);
        sb.append(", value=").append(value);
        sb.append(", text=").append(text);
        sb.append(", isStop=").append(isStop);
        sb.append(", comet=").append(comet);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}