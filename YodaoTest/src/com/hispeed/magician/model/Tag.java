package com.hispeed.magician.model;

import java.io.Serializable;
import java.util.Date;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 相册标签model
 * 
 * @author wangy
 * @version
 * @date 2015年2月3日
 * 
 */
@Entity
@Table(name = "pho_tag")
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "uid")
    private long uid;

    @Column(name = "name")
    private String name;

    @Column(name = "created_time")
    private Date createdTime;

    // 图片类型：0-脸型，1-发型，2-未定义。可自己定义
    @Column(name = "type")
    private int type;

    @Column(name = "sort")
    private int sort;

    @Column(name = "op_status")
    private int opStatus;

    @Column(name = "version")
    private long version;

    @Column(name = "count")
    private int count;

    public Tag() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Tag [id=" + id + ", uid=" + uid + ", name=" + name
                + ", createdTime=" + createdTime + ", type=" + type + ", sort="
                + sort + ", opStatus=" + opStatus + ", version=" + version
                + "]";
    }

}