package com.cff.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "susers")
public class User {
	@Id
	@NotNull
	@Column(name = "userName")
	String userName;
	@Column(name = "passwd")
	String passwd;
	@Column(name = "name")
	String name;
	@Column(name = "emailAddr")
	String emailAddr;
	@Column(name = "mobile")
	String mobile;
	@Column(name = "wechat")
	String wechat;
	@Column(name = "role")
	String role;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", passwd=" + passwd + ", name=" + name + ", emailAddr=" + emailAddr
				+ ", mobile=" + mobile + ", wechat=" + wechat + ", role=" + role + "]";
	}

}
