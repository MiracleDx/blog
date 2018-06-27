package com.dongx.blog.entity;

import java.io.Serializable;

public class total implements Serializable {
    private Integer id;

    private String userid;

    private String refid;

    private Integer likeTotal;

    private Integer replayTotal;

    private Integer totaltype;

    private Integer reftype;

    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid == null ? null : refid.trim();
    }

    public Integer getLikeTotal() {
        return likeTotal;
    }

    public void setLikeTotal(Integer likeTotal) {
        this.likeTotal = likeTotal;
    }

    public Integer getReplayTotal() {
        return replayTotal;
    }

    public void setReplayTotal(Integer replayTotal) {
        this.replayTotal = replayTotal;
    }

    public Integer getTotaltype() {
        return totaltype;
    }

    public void setTotaltype(Integer totaltype) {
        this.totaltype = totaltype;
    }

    public Integer getReftype() {
        return reftype;
    }

    public void setReftype(Integer reftype) {
        this.reftype = reftype;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "total{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", refid='" + refid + '\'' +
                ", likeTotal=" + likeTotal +
                ", replayTotal=" + replayTotal +
                ", totaltype=" + totaltype +
                ", reftype=" + reftype +
                ", status=" + status +
                '}';
    }
}