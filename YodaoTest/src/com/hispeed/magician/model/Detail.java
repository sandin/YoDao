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
 * 消费明细model
 * 
 * @author wangy
 * @version
 * @date 2015年1月19日
 * 
 */
@Entity
@Table(name = "crm_history")
public class Detail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "cid")
    private long cid;

    @Column(name = "money")
    private double money;

    @Column(name = "consume_date")
    private Date consumeDate;

    @Column(name = "consume_time")
    private int consumeTime;

    @Column(name = "is_appoint")
    private int isAppoint;

    @Column(name = "type")
    private int type;

    @Column(name = "status")
    private int status;

    @Column(name = "coupon_id")
    private int couponId;

    @Column(name = "consumption_type")
    private int consumptionType;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "state")
    private int state;

    public Detail() {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getConsumptionType() {
        return consumptionType;
    }

    public void setConsumptionType(int consumptionType) {
        this.consumptionType = consumptionType;
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

    @Override
    public String toString() {
        return "Detail [id=" + id + ", cid=" + cid + ", money=" + money
                + ", consumeDate=" + consumeDate + ", consumeTime="
                + consumeTime + ", isAppoint=" + isAppoint + ", type=" + type
                + ", status=" + status + ", couponId=" + couponId
                + ", consumptionType=" + consumptionType + ", createdTime="
                + createdTime + ", state=" + state + "]";
    }

}