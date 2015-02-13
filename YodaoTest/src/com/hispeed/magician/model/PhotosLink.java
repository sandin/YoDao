package com.hispeed.magician.model;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 
 * 
 * photo_photo 关联表
 * 
 * @author wellor
 * @version
 * @date 2015-2-10
 * 
 */
@Entity
@Table(name = "pho_photos_link")
public class PhotosLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "photo_id")
    private long photoId;

    @Column(name = "another_photo_id")
    private long anotherPhotoId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public long getAnotherPhotoId() {
        return anotherPhotoId;
    }

    public void setAnotherPhotoId(long anotherPhotoId) {
        this.anotherPhotoId = anotherPhotoId;
    }

    @Override
    public String toString() {
        return "PhotosLink [id=" + id + ", photoId=" + photoId
                + ", anotherPhotoId=" + anotherPhotoId + "]";
    }
}
