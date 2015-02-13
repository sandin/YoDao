package com.hispeed.magician.model;

import java.io.Serializable;
import java.util.Date;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.NotColumn;
import com.yoda.yodao.annotation.Table;

/**
 * 顾客model
 * 
 * @author wangy
 * @version
 * @date 2015年1月19日
 * 
 */
@Entity
@Table(name = "crm_customer")
public class Customer implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private long uid;

	private String name;

	private String avatar;

	private int age;

	private int sex;

	private String profile;

	private String job;

	private String address;

	private String cellphone;

	private String qq;

	private String wechat;

	private int hairStyle;

	private int faceShape;

	private int hairCount;

	private int hairQuality;

	private int isVip;

	private int state;

	private Date recentDate;

	private double total;

	private int times;

	private Date createdTime;

	private long password;

	public Customer() {
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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public int getHairStyle() {
		return hairStyle;
	}

	public void setHairStyle(int hairStyle) {
		this.hairStyle = hairStyle;
	}

	public int getFaceShape() {
		return faceShape;
	}

	public void setFaceShape(int faceShape) {
		this.faceShape = faceShape;
	}

	public int getHairCount() {
		return hairCount;
	}

	public void setHairCount(int hairCount) {
		this.hairCount = hairCount;
	}

	public int getHairQuality() {
		return hairQuality;
	}

	public void setHairQuality(int hairQuality) {
		this.hairQuality = hairQuality;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getRecentDate() {
		return recentDate;
	}

	public void setRecentDate(Date recentDate) {
		this.recentDate = recentDate;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public long getPassword() {
		return password;
	}

	public void setPassword(long password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", uid=" + uid + ", name=" + name
				+ ", avatar=" + avatar + ", age=" + age + ", sex=" + sex
				+ ", profile=" + profile + ", job=" + job + ", address="
				+ address + ", cellphone=" + cellphone + ", qq=" + qq
				+ ", wechat=" + wechat + ", hairStyle=" + hairStyle
				+ ", faceShape=" + faceShape + ", hairCount=" + hairCount
				+ ", hairQuality=" + hairQuality + ", isVip=" + isVip
				+ ", state=" + state + ", recentDate=" + recentDate
				+ ", total=" + total + ", times=" + times + ", createdTime="
				+ createdTime + "]";
	}

}