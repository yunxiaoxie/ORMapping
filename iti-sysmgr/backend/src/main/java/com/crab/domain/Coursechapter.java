package com.crab.domain;

import java.io.Serializable;
import java.util.Date;

public class Coursechapter implements Serializable {
    private Integer id;

    private Integer courseId;

    private String chapter;

    private String videoUrl;

    private String videoCode;

    private String videoComm;

    private String sourcecodeUrl;

    private String sourcecodeCode;

    private Date createTime;

    private String createUser;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter == null ? null : chapter.trim();
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl == null ? null : videoUrl.trim();
    }

    public String getVideoCode() {
        return videoCode;
    }

    public void setVideoCode(String videoCode) {
        this.videoCode = videoCode == null ? null : videoCode.trim();
    }

    public String getVideoComm() {
        return videoComm;
    }

    public void setVideoComm(String videoComm) {
        this.videoComm = videoComm == null ? null : videoComm.trim();
    }

    public String getSourcecodeUrl() {
        return sourcecodeUrl;
    }

    public void setSourcecodeUrl(String sourcecodeUrl) {
        this.sourcecodeUrl = sourcecodeUrl == null ? null : sourcecodeUrl.trim();
    }

    public String getSourcecodeCode() {
        return sourcecodeCode;
    }

    public void setSourcecodeCode(String sourcecodeCode) {
        this.sourcecodeCode = sourcecodeCode == null ? null : sourcecodeCode.trim();
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
        Coursechapter other = (Coursechapter) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCourseId() == null ? other.getCourseId() == null : this.getCourseId().equals(other.getCourseId()))
            && (this.getChapter() == null ? other.getChapter() == null : this.getChapter().equals(other.getChapter()))
            && (this.getVideoUrl() == null ? other.getVideoUrl() == null : this.getVideoUrl().equals(other.getVideoUrl()))
            && (this.getVideoCode() == null ? other.getVideoCode() == null : this.getVideoCode().equals(other.getVideoCode()))
            && (this.getVideoComm() == null ? other.getVideoComm() == null : this.getVideoComm().equals(other.getVideoComm()))
            && (this.getSourcecodeUrl() == null ? other.getSourcecodeUrl() == null : this.getSourcecodeUrl().equals(other.getSourcecodeUrl()))
            && (this.getSourcecodeCode() == null ? other.getSourcecodeCode() == null : this.getSourcecodeCode().equals(other.getSourcecodeCode()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCourseId() == null) ? 0 : getCourseId().hashCode());
        result = prime * result + ((getChapter() == null) ? 0 : getChapter().hashCode());
        result = prime * result + ((getVideoUrl() == null) ? 0 : getVideoUrl().hashCode());
        result = prime * result + ((getVideoCode() == null) ? 0 : getVideoCode().hashCode());
        result = prime * result + ((getVideoComm() == null) ? 0 : getVideoComm().hashCode());
        result = prime * result + ((getSourcecodeUrl() == null) ? 0 : getSourcecodeUrl().hashCode());
        result = prime * result + ((getSourcecodeCode() == null) ? 0 : getSourcecodeCode().hashCode());
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
        sb.append(", courseId=").append(courseId);
        sb.append(", chapter=").append(chapter);
        sb.append(", videoUrl=").append(videoUrl);
        sb.append(", videoCode=").append(videoCode);
        sb.append(", videoComm=").append(videoComm);
        sb.append(", sourcecodeUrl=").append(sourcecodeUrl);
        sb.append(", sourcecodeCode=").append(sourcecodeCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}