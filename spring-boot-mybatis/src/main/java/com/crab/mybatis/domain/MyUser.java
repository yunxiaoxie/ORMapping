package com.crab.mybatis.domain;

import java.io.Serializable;
import java.util.Date;

public class MyUser implements Serializable {
    /**
     *
     *  主键, 所属表字段为my_user.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     *  用户名, 所属表字段为my_user.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     *
     *  性别, 所属表字段为my_user.sex
     *
     * @mbg.generated
     */
    private String sex;

    /**
     *
     *  年龄, 所属表字段为my_user.age
     *
     * @mbg.generated
     */
    private Integer age;

    /**
     *
     *  地址, 所属表字段为my_user.addr
     *
     * @mbg.generated
     */
    private String addr;

    /**
     *
     *  电话, 所属表字段为my_user.tel
     *
     * @mbg.generated
     */
    private String tel;

    /**
     *
     *  是否成人, 所属表字段为my_user.is_adult
     *
     * @mbg.generated
     */
    private String is_adult;

    /**
     *
     *  兴趣, 所属表字段为my_user.intrest
     *
     * @mbg.generated
     */
    private String intrest;

    /**
     *
     *  生日, 所属表字段为my_user.birthday
     *
     * @mbg.generated
     */
    private Date birthday;

    /**
     *
     *  体重, 所属表字段为my_user.weight
     *
     * @mbg.generated
     */
    private Double weight;

    /**
     *
     *  创建时间, 所属表字段为my_user.create_time
     *
     * @mbg.generated
     */
    private Date create_time;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getIs_adult() {
        return is_adult;
    }

    public void setIs_adult(String is_adult) {
        this.is_adult = is_adult == null ? null : is_adult.trim();
    }

    public String getIntrest() {
        return intrest;
    }

    public void setIntrest(String intrest) {
        this.intrest = intrest == null ? null : intrest.trim();
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
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
        MyUser other = (MyUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getAge() == null ? other.getAge() == null : this.getAge().equals(other.getAge()))
            && (this.getAddr() == null ? other.getAddr() == null : this.getAddr().equals(other.getAddr()))
            && (this.getTel() == null ? other.getTel() == null : this.getTel().equals(other.getTel()))
            && (this.getIs_adult() == null ? other.getIs_adult() == null : this.getIs_adult().equals(other.getIs_adult()))
            && (this.getIntrest() == null ? other.getIntrest() == null : this.getIntrest().equals(other.getIntrest()))
            && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
            && (this.getWeight() == null ? other.getWeight() == null : this.getWeight().equals(other.getWeight()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getAge() == null) ? 0 : getAge().hashCode());
        result = prime * result + ((getAddr() == null) ? 0 : getAddr().hashCode());
        result = prime * result + ((getTel() == null) ? 0 : getTel().hashCode());
        result = prime * result + ((getIs_adult() == null) ? 0 : getIs_adult().hashCode());
        result = prime * result + ((getIntrest() == null) ? 0 : getIntrest().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getWeight() == null) ? 0 : getWeight().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", age=").append(age);
        sb.append(", addr=").append(addr);
        sb.append(", tel=").append(tel);
        sb.append(", is_adult=").append(is_adult);
        sb.append(", intrest=").append(intrest);
        sb.append(", birthday=").append(birthday);
        sb.append(", weight=").append(weight);
        sb.append(", create_time=").append(create_time);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}