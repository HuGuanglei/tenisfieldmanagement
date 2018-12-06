package com.cff.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "scourtinfo")
public class ScourtInfo {
	@Id
	@NotNull
	@Column(name = "courtId")
	int courtId;
	
	@Column(name = "createDate")
	String createDate;
	
	@Column(name = "courtName")
	String courtName;

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getCourtId() {
		return courtId;
	}

	public void setCourtId(int courtId) {
		this.courtId = courtId;
	}

	public String getCourtName() {
		return courtName;
	}

	public void setCourtName(String courtName) {
		this.courtName = courtName;
	}
}
