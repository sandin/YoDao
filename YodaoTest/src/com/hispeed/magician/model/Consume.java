package com.hispeed.magician.model;

import java.io.Serializable;

import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.Table;

/**
 * 
 * 
 * @author wellor
 * @version
 * @date 2015-2-10
 * 
 */

@Deprecated
@Entity
@Table(name = "crm_consume")
public class Consume implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "detail")
    private Detail detail;

    @Column(name = "review")
    private Review review;

    @Column(name = "modeling")
    private Modeling modeling;

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public Modeling getModeling() {
        return modeling;
    }

    public void setModeling(Modeling modeling) {
        this.modeling = modeling;
    }

    @Override
    public String toString() {
        return "Consume [detail=" + detail + ", review=" + review
                + ", modeling=" + modeling + "]";
    }

}
