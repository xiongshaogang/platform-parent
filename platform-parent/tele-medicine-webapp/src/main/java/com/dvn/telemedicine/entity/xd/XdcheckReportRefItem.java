package com.dvn.telemedicine.entity.xd;

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
@Table(name = "xd_check_report_ref_item")
public class XdcheckReportRefItem implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private Long itemId;
	private Long reportId;
	private String testValue;//
	private Date createDate;
	
	// Constructors

	/** default constructor */
	public XdcheckReportRefItem() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XdcheckReportRefItem(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XdcheckReportRefItem(Long id, Date createDate) {
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
	@Column(name = "item_id")
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	@Column(name = "report_id")
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	@Column(name = "test_value")
	public String getTestValue() {
		return testValue;
	}

	public void setTestValue(String testValue) {
		this.testValue = testValue;
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