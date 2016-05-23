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
@Table(name = "xd_check_report_waveshape")
public class XdcheckReportWaveshape implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private Long reportId;
	private String inWh;
	private String outWh;
	private Date createDate;

	// Constructors

	/** default constructor */
	public XdcheckReportWaveshape() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XdcheckReportWaveshape(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XdcheckReportWaveshape(Long id, Date createDate) {
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
	
	@Column(name = "report_id")
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
	
	@Column(name = "in_wh")
	public String getInWh() {
		return inWh;
	}

	public void setInWh(String inWh) {
		this.inWh = inWh;
	}
	@Column(name = "out_wh")
	public String getOutWh() {
		return outWh;
	}

	public void setOutWh(String outWh) {
		this.outWh = outWh;
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