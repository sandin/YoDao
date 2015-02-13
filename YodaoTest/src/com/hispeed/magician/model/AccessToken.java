package com.hispeed.magician.model;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 微信通用接口凭证
 * 
 */
@Entity
@Table(name = "sys_token")
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // 获取到的凭证
    @Column(name = "access_token")
    private String accessToken;

    // 凭证有效时间 秒，-1永不过期
    @Column(name = "expires_in")
    private int expiresIn;

    // 过期后刷新token
    @Column(name = "refresh_token")
    private String refreshToken;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}