package com.dvn.telemedicine.entity.x;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Eclipse Persistence Tools
 */
@Entity
@Table(name = "x_check_report_blood_pressure")
public class XcheckReportBloodPressure implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private String memberId;
	private Long pr;
	private String bloodPressure;//
	private Date createDate;

	// Constructors

	/** default constructor */
	public XcheckReportBloodPressure() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XcheckReportBloodPressure(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XcheckReportBloodPressure(Long id, Date createDate) {
		this.id = id;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "member_id")
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	@Column(name = "pr")
	public Long getPr() {
		return pr;
	}

	public void setPr(Long pr) {
		this.pr = pr;
	}
	@Column(name = "blood_pressure")
	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}