package com.dvn.telemedicine.entity.xy;

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
@Table(name = "xy_check_report")
public class XycheckReport implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private String memberId;
	private String testValue;
	private Integer level;
	private Date createDate;

	// Constructors

	/** default constructor */
	public XycheckReport() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XycheckReport(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XycheckReport(Long id, Date createDate) {
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

	@Column(name = "test_value")
	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}
	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
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