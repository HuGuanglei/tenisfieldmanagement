package com.cff.domain.wallet;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the account_transflow database table.
 * 
 */
@Entity
@Table(name="account_transflow")
@NamedQuery(name="AccountTransflow.findAll", query="SELECT a FROM AccountTransflow a")
public class AccountTransflow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String flag;

	@Column(name="trans_amt")
	private int transAmt;

	@Column(name="trans_date")
	private String transDate;

	@Column(name="trans_time")
	private String transTime;

	@Column(name="trans_type")
	private String transType;

	@Column(name="user_name")
	private String userName;
	
	@Column(name="remark")
	private String remark;

	public AccountTransflow() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getTransAmt() {
		return this.transAmt;
	}

	public void setTransAmt(int transAmt) {
		this.transAmt = transAmt;
	}

	public String getTransDate() {
		return this.transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return this.transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}