package com.dvn.telemedicine.entity.x;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * XCheckReportJsonResult entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "x_check_report_json_result")
public class XCheckReportJsonResult implements java.io.Serializable {

	// Fields

	private Long reportId;
	private String jsonData;

	// Constructors

	/** default constructor */
	public XCheckReportJsonResult() {
	}

	/** full constructor */
	public XCheckReportJsonResult(String jsonData) {
		this.jsonData = jsonData;
	}

	// Property accessors
	@Id
	@Column(name = "report_id", unique = true, nullable = false)
	public Long getReportId() {
		return this.reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	@Column(name = "json_data", length = 65535)
	public String getJsonData() {
		return this.jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

}