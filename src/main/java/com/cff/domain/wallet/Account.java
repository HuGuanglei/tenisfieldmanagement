package com.cff.domain.wallet;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the account database table.
 * 
 */
@Entity
@Table(name="account")
@NamedQuery(name="Account.findAll", query="SELECT a FROM Account a")
public class Account implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="account_id", unique=true, nullable=false)
	private int accountId;

	@Column(name="acc_type", nullable=false, length=3)
	private String accType;

	@Column(nullable=false)
	private int bal;

	@Column(name="close_time", length=14)
	private String closeTime;

	@Column(name="lock_flag", length=2)
	private String lockFlag;

	@Column(name="open_time", length=14)
	private String openTime;

	@Column(nullable=false, length=2)
	private String status;

	@Column(name="user_name", nullable=false, length=45)
	private String userName;

	public Account() {
	}

	public int getAccountId() {
		return this.accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccType() {
		return this.accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public int getBal() {
		return this.bal;
	}

	public void setBal(int bal) {
		this.bal = bal;
	}

	public String getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getLockFlag() {
		return this.lockFlag;
	}

	public void setLockFlag(String lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getOpenTime() {
		return this.openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}