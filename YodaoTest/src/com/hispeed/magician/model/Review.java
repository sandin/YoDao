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
 * 回访表model
 * 
 * @author wangy
 * @version
 * @date 2015年1月19日
 * 
 */
@Entity
@Table(name = "crm_review")
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "detail_id")
    private long detailId;

    @Column(name = "visit_content")
    private String visitContent;

    @Column(name = "visit_time")
    private Date visitTime;

    @Column(name = "coupon_id")
    private int couponId;

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
    private String evaluateContent;

    @Column(name = "evaluate_time")
    private Date evaluateTime;

    @Column(name = "state")
    private int state;

    public Review() {
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

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Review [id=" + id + ", detailId=" + detailId
                + ", visitContent=" + visitContent + ", visitTime=" + visitTime
                + ", couponId=" + couponId + ", level=" + level
                + ", skillScore=" + skillScore + ", serviceScore="
                + serviceScore + ", storeScore=" + storeScore
                + ", chargeScore=" + chargeScore + ", evaluateContent="
                + evaluateContent + ", evaluateTime=" + evaluateTime
                + ", state=" + state + "]";
    }

}