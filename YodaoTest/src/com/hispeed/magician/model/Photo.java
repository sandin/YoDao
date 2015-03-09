package com.hispeed.magician.model;

import java.io.Serializable;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 图片实体
 * 
 * @author wellor
 * @version
 * @date 2015-3-2
 * @class Photo2.java
 * 
 */
@Entity
@Table(name = "pho_photo")
public class Photo implements Serializable {

    @Column(name = "id")
    private long id;

    /** uuid */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    /** 发型uuid */
    @Column(name = "hair_id")
    private String hairId;

    /** 照片路径 */
    @Column(name = "path")
    private String path;

    /** hash */
    @Column(name = "hash")
    private String hash;

    /** 服务器路径 */
    @Column(name = "url")
    private String url;

    /** 文件大小 */
    @Column(name = "size")
    private long size;

    /** 操作状态 0insert 1del 2update */
    @Column(name = "op_status")
    private int opStatus;

    /** 排序 */
    @Column(name = "sort")
    private int sort;

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

    public String getHairId() {
        return hairId;
    }

    public void setHairId(String hairId) {
        this.hairId = hairId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Photo2 [id=" + id + ", uuid=" + uuid + ", hairId=" + hairId + ", path=" + path
                + ", hash=" + hash + ", url=" + url + ", size=" + size + ", opStatus=" + opStatus
                + ", sort=" + sort + "]";
    }

}
