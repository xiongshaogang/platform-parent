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
@Table(name = "xx_check_report_blood_week_avg")
public class XxcheckReportBloodWeekAvg implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private String memberId;
	private String date;
	private String time;//
	private String bloodAvg;//
	private Date createDate;

	// Constructors

	/** default constructor */
	public XxcheckReportBloodWeekAvg() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XxcheckReportBloodWeekAvg(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XxcheckReportBloodWeekAvg(Long id, Date createDate) {
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
	@Column(name = "date")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	@Column(name = "time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	@Column(name = "blood_avg")
	public String getBloodAvg() {
		return bloodAvg;
	}

	public void setBloodAvg(String bloodAvg) {
		this.bloodAvg = bloodAvg;
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