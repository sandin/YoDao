package com.hispeed.magician.model;

import java.io.Serializable;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 造型model
 * 
 * @author wangy
 * @version
 * @date 2015年1月19日
 * 
 */
@Entity
@Table(name = "crm_modeling")
public class Modeling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "detail_id")
    private long detailId;

    @Column(name = "before_photo_id")
    private int beforePhotoId;

    @Column(name = "after_photo_id")
    private int afterPhotoId;

    @Column(name = "preview_photo_id")
    private int previewPhotoId;

    @Column(name = "state")
    private int state;

    public Modeling() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDetailId() {
        return detailId;
    }

    public void setDetailId(long detailId) {
        this.detailId = detailId;
    }

    public int getBeforePhotoId() {
        return beforePhotoId;
    }

    public void setBeforePhotoId(int beforePhotoId) {
        this.beforePhotoId = beforePhotoId;
    }

    public int getAfterPhotoId() {
        return afterPhotoId;
    }

    public void setAfterPhotoId(int afterPhotoId) {
        this.afterPhotoId = afterPhotoId;
    }

    public int getPreviewPhotoId() {
        return previewPhotoId;
    }

    public void setPreviewPhotoId(int previewPhotoId) {
        this.previewPhotoId = previewPhotoId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Modeling [id=" + id + ", detailId=" + detailId
                + ", beforePhotoId=" + beforePhotoId + ", afterPhotoId="
                + afterPhotoId + ", previewPhotoId=" + previewPhotoId
                + ", state=" + state + "]";
    }

}