package com.yoda.yodao.test.model;

import java.util.List;

import com.yoda.yodao.annotation.CascadeType;
import com.yoda.yodao.annotation.Column;
import com.yoda.yodao.annotation.Entity;
import com.yoda.yodao.annotation.FetchType;
import com.yoda.yodao.annotation.GeneratedValue;
import com.yoda.yodao.annotation.GenerationType;
import com.yoda.yodao.annotation.Id;
import com.yoda.yodao.annotation.JoinColumn;
import com.yoda.yodao.annotation.OneToMany;
import com.yoda.yodao.annotation.OneToOne;
import com.yoda.yodao.annotation.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "age")
	private long age;

	private String privateField;

	public User() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getPrivateField() {
		return privateField;
	}

	public void setPrivateField(String privateField) {
		this.privateField = privateField;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age
				+ ", privateField=" + privateField + "]";
	}

}
