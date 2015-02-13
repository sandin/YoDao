package com.hispeed.magician.model;

import java.io.Serializable;

public class Contact implements Serializable {
    private long id;
    private String name;
    private String avatar;
    private String phone;
    private String SortLetters;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSortLetters() {
        return SortLetters;
    }

    public void setSortLetters(String sortLetters) {
        SortLetters = sortLetters;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", name=" + name + ", avatar=" + avatar
                + ", phone=" + phone + "]";
    }

}
