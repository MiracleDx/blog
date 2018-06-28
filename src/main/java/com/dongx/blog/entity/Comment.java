package com.dongx.blog.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blog_comment")
public class Comment implements Serializable {
    
    @Id
    private String id;

    private String blogId;

    private String content;

    private String createUser;

    private Date createTime;

    private Integer status;

    private String pid;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId == null ? null : blogId.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    @Override
    public String toString() {
        return "CommentVo{" +
                "id='" + id + '\'' +
                ", blogId='" + blogId + '\'' +
                ", content='" + content + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", pid='" + pid + '\'' +
                '}';
    }
}