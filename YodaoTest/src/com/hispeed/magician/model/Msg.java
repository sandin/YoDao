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
 * 信息模板model
 * 
 * @author wangy
 * @version
 * @date 2015年1月19日
 * 
 */
@Entity
@Table(name = "crm_msg")
public class Msg implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "uid")
    private long uid;

    @Column(name = "msg_type")
    private int msgType;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "op_status")
    private int opStatus;

    @Column(name = "version")
    private int version;

    public Msg() {
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

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
        return "Msg [id=" + id + ", uid=" + uid + ", msgType=" + msgType
                + ", content=" + content + ", createdDate=" + createdDate
                + ", opStatus=" + opStatus + ", version=" + version + "]";
    }

}