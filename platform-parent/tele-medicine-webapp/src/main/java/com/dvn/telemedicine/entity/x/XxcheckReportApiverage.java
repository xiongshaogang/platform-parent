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
 * 平均脉压
 */
@Entity
@Table(name = "xx_check_report_apiverage")
public class XxcheckReportApiverage implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String memberId;
	private String pulse;
	private String bloodPressure;
	private Date createDate;
	private Long groupId;

	/** default constructor */
	public XxcheckReportApiverage() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XxcheckReportApiverage(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XxcheckReportApiverage(Long id, Date createDate) {
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
	@Column(name = "pulse")
	public String getPulse() {
		return pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
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

	@Column(name = "group_id")
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	
}