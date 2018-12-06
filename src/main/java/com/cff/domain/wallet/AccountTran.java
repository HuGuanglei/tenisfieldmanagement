package com.cff.domain.wallet;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the account_trans database table.
 * 
 */
@Entity
@Table(name="account_trans")
@NamedQuery(name="AccountTran.findAll", query="SELECT a FROM AccountTran a")
public class AccountTran implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(length=1)
	private String flag;

	@Column(name="trans_name", length=20)
	private String transName;

	@Column(name="trans_type", length=4)
	private String transType;

	public AccountTran() {
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTransName() {
		return this.transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getTransType() {
		return this.transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}