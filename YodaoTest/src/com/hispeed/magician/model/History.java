package com.hispeed.magician.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 消费明细model
 * 
 * @author wangy
 * @version
 * @date 2015年1月19日
 * 
 */
@Entity
@Table(name = "crm_history")
public class History implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // 顾客id
    @Column(name = "cid")
    private long cid;

    @Column(name = "money")
    private double money;

    @Column(name = "consume_date")
    private Date consumeDate;

    // 消费时间：0-上午，1-中午，2-晚上
    @Column(name = "consume_time")
    private int consumeTime;

    // 是否指定：0-未指定，1-指定
    @Column(name = "is_appoint")
    private int isAppoint;

    // 支付方式：0-卡消，1-现金
    @Column(name = "type")
    private int type;

    // 优惠券id
    @Column(name = "coupon_id")
    private int couponId;

    // 消费项目
    @Column(name = "consumption_type")
    private String consumptionType;

    /** 造型 */
    // 造型前
    @Column(name = "before_photo_id")
    private long beforePhotoId;

    // 造型后
    @Column(name = "after_photo_id")
    private long afterPhotoId;

    // 预览图
    @Column(name = "preview_photo_id")
    private long previewPhotoId;

    /** 回访 */
    @Column(name = "visit_content")
    private String visitContent = "";

    @Column(name = "visit_time")
    private Date visitTime;

    @Column(name = "review_coupon_id")
    private int reviewCouponId;

    @Column(name = "level")
    private int level;

    @Column(name = "skill_score")
    private int skillScore;

    @Column(name = "service_score")
    private int serviceScore;

    @Column(name = "store_score")
    private int storeScore;

    @Column(name = "charge_score")
    private int chargeScore;

    @Column(name = "evaluate_content")
    private String evaluateContent = "";

    @Column(name = "evaluate_time")
    private Date evaluateTime;

    @Column(name = "created_time")
    private Date createdTime;

    // 回访状态：0-未回访，1-已回访，2-无回复
    @Column(name = "status")
    private int status;

    @Column(name = "version")
    private long version;

    @Column(name = "op_status")
    private int opStatus;

    private Set<Photo> photos = new HashSet<Photo>();

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public int getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(int opStatus) {
        this.opStatus = opStatus;
    }

    public History() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(Date consumeDate) {
        this.consumeDate = consumeDate;
    }

    public int getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(int consumeTime) {
        this.consumeTime = consumeTime;
    }

    public int getIsAppoint() {
        return isAppoint;
    }

    public void setIsAppoint(int isAppoint) {
        this.isAppoint = isAppoint;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(String consumptionType) {
        this.consumptionType = consumptionType;
    }

    public long getBeforePhotoId() {
        return beforePhotoId;
    }

    public void setBeforePhotoId(long beforePhotoId) {
        this.beforePhotoId = beforePhotoId;
    }

    public long getAfterPhotoId() {
        return afterPhotoId;
    }

    public void setAfterPhotoId(long afterPhotoId) {
        this.afterPhotoId = afterPhotoId;
    }

    public long getPreviewPhotoId() {
        return previewPhotoId;
    }

    public void setPreviewPhotoId(long previewPhotoId) {
        this.previewPhotoId = previewPhotoId;
    }

    public String getVisitContent() {
        return visitContent;
    }

    public void setVisitContent(String visitContent) {
        this.visitContent = visitContent;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public int getReviewCouponId() {
        return reviewCouponId;
    }

    public void setReviewCouponId(int reviewCouponId) {
        this.reviewCouponId = reviewCouponId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSkillScore() {
        return skillScore;
    }

    public void setSkillScore(int skillScore) {
        this.skillScore = skillScore;
    }

    public int getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }

    public int getStoreScore() {
        return storeScore;
    }

    public void setStoreScore(int storeScore) {
        this.storeScore = storeScore;
    }

    public int getChargeScore() {
        return chargeScore;
    }

    public void setChargeScore(int chargeScore) {
        this.chargeScore = chargeScore;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "History [id=" + id + ", cid=" + cid + ", money=" + money
                + ", consumeDate=" + consumeDate + ", consumeTime="
                + consumeTime + ", isAppoint=" + isAppoint + ", type=" + type
                + ", couponId=" + couponId + ", consumptionType="
                + consumptionType + ", beforePhotoId=" + beforePhotoId
                + ", afterPhotoId=" + afterPhotoId + ", previewPhotoId="
                + previewPhotoId + ", visitContent=" + visitContent
                + ", visitTime=" + visitTime + ", reviewCouponId="
                + reviewCouponId + ", level=" + level + ", skillScore="
                + skillScore + ", serviceScore=" + serviceScore
                + ", storeScore=" + storeScore + ", chargeScore=" + chargeScore
                + ", evaluateContent=" + evaluateContent + ", evaluateTime="
                + evaluateTime + ", createdTime=" + createdTime + ", status="
                + status + ", version=" + version + ", opStatus=" + opStatus
                + "]";
    }

}