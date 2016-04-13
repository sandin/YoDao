package com.yoda.yodao.example.model;

import java.io.Serializable;
import java.util.Date;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 发型 实体
 * 
 * @author wellor
 * @version
 * @date 2015-3-2
 * @class Hair.java
 * 
 */
@Entity
@Table(name = "pho_hairHa")
public class Hair implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "uuid")
    private String uuid; //

    @Column(name = "uid")
    private long uid; // 发型师id

    @Column(name = "name")
    private String name = ""; // 描述

    @Column(name = "content")
    private String content = "";// 图片介绍

    @Column(name = "cover")
    private String cover = "";// 封面地址

    @Column(name = "sort")
    private int sort; // 0 排序

    @Column(name = "resource")
    private int resource; // 来源：0-系统，1-自动。备注（系统不用同步）

    @Column(name = "created_time")
    private Date createdTime; // null

    @Column(name = "state")
    private int state; // 是否可用，0不可用，1可用

    @Column(name = "op_status")
    private int opStatus; // 0 操作状态，0-新增，1-删除，2-修改

    @Column(name = "version")
    private int version; // 0 版本值

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Hair [id=" + id + ", uuid=" + uuid + ", uid=" + uid + ", name=" + name
                + ", content=" + content + ", cover=" + cover + ", sort=" + sort + ", resource="
                + resource + ", createdTime=" + createdTime + ", state=" + state + ", opStatus="
                + opStatus + ", version=" + version + "]";
    }

}
