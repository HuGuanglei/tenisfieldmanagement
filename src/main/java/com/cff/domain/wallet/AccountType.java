package com.cff.domain.wallet;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the account_type database table.
 * 
 */
@Entity
@Table(name="account_type")
@NamedQuery(name="AccountType.findAll", query="SELECT a FROM AccountType a")
public class AccountType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name="acc_name", nullable=false, length=20)
	private String accName;

	@Column(name="acc_type", nullable=false, length=3)
	private String accType;
	
	@Column(name="init_amt")
	private int initAmt;

	@Column(name="valid_flag", length=1)
	private String validFlag;

	public AccountType() {
	}

	public String getAccName() {
		return this.accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccType() {
		return this.accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getValidFlag() {
		return this.validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInitAmt() {
		return initAmt;
	}

	public void setInitAmt(int initAmt) {
		this.initAmt = initAmt;
	}

}