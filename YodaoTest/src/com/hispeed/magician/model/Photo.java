package com.hispeed.magician.model;

import java.io.Serializable;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

@Entity
@Table(name = "pho_photo")
public class Photo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "uid")
    private long uid;

    @Column(name = "name")
    private String name = "";
    @Column(name = "path")
    private String path = "";

    @Column(name = "hash")
    private String hash = "";

    @Column(name = "content")
    private String content = "";

    @Column(name = "url")
    private String url = "";

    @Column(name = "view_type")
    private int viewType;

    @Column(name = "size")
    private long size;

    @Column(name = "op_status")
    private int opStatus;

    @Column(name = "version")
    private long version;

    public Photo() {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
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

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Photo [id=" + id + ", uid=" + uid + ", name=" + name
                + ", path=" + path + ", hash=" + hash + ", content=" + content
                + ", url=" + url + ", viewType=" + viewType + ", size=" + size
                + ", opStatus=" + opStatus + ", version=" + version + "]";
    }

}