package com.cff.domain.wallet;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the account_flow database table.
 * 
 */
@Entity
@Table(name="account_flow")
@NamedQuery(name="AccountFlow.findAll", query="SELECT a FROM AccountFlow a")
public class AccountFlow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="opp_accid")
	private int oppAccid;

	@Column(name="opp_amt")
	private int oppAmt;

	@Column(name="trans_accid")
	private int transAccid;

	@Column(name="trans_amt")
	private int transAmt;

	@Column(name="trans_bal")
	private int transBal;

	@Column(name="trans_date")
	private String transDate;

	@Column(name="trans_flow")
	private int transFlow;

	@Column(name="trans_time")
	private String transTime;

	@Column(name="trans_type")
	private String transType;
	
	@Column(name="dir")
	private String dir;

	public AccountFlow() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOppAccid() {
		return this.oppAccid;
	}

	public void setOppAccid(int oppAccid) {
		this.oppAccid = oppAccid;
	}

	public int getOppAmt() {
		return this.oppAmt;
	}

	public void setOppAmt(int oppAmt) {
		this.oppAmt = oppAmt;
	}

	public int getTransAccid() {
		return this.transAccid;
	}

	public void setTransAccid(int transAccid) {
		this.transAccid = transAccid;
	}

	public int getTransAmt() {
		return this.transAmt;
	}

	public void setTransAmt(int transAmt) {
		this.transAmt = transAmt;
	}

	public int getTransBal() {
		return this.transBal;
	}

	public void setTransBal(int transBal) {
		this.transBal = transBal;
	}

	public String getTransDate() {
		return this.transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public int getTransFlow() {
		return this.transFlow;
	}

	public void setTransFlow(int transFlow) {
		this.transFlow = transFlow;
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

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

}