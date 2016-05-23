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
@Table(name = "xx_check_report_blood_week_data")
public class XxcheckReportBloodWeekData implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private Long weekId;
	private String bloodAvg;//
	private Date date;
	private Date createDate;

	// Constructors

	/** default constructor */
	public XxcheckReportBloodWeekData() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XxcheckReportBloodWeekData(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XxcheckReportBloodWeekData(Long id, Date createDate) {
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
	@Column(name = "week_id")
	public Long getWeekId() {
		return weekId;
	}

	public void setWeekId(Long weekId) {
		this.weekId = weekId;
	}

	@Column(name = "date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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