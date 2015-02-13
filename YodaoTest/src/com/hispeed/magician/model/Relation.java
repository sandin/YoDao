package com.hispeed.magician.model;

import java.util.Date;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

@Table(name = "crm_contact")
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "cid")
    private long cid;
    @Column(name = "contact_time")
    private Date contactTime;

    @Column(name = "content")
    private String content;

    @Column(name = "coupon_id")
    private long couponId;

    @Column(name = "photo_id")
    private long photoId;

    @Column(name = "msg_type")
    private int msgType;

    @Column(name = "send_type")
    private int sendType;

    @Column(name = "version")
    private long version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCid() {
        return cid;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public Date getContactTime() {
        return contactTime;
    }

    public void setContactTime(Date contactTime) {
        this.contactTime = contactTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCouponId() {
        return couponId;
    }

    public void setCouponId(long couponId) {
        this.couponId = couponId;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public int getSendType() {
        return sendType;
    }

    public void setSendType(int sendType) {
        this.sendType = sendType;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Relation [id=" + id + ", cid=" + cid + ", contactTime="
                + contactTime + ", content=" + content + ", couponId="
                + couponId + ", photoId=" + photoId + ", msgType=" + msgType
                + ", sendType=" + sendType + ", version=" + version + "]";
    }

}
