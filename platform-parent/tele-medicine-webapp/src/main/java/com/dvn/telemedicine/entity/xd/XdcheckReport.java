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
@Table(name = "xd_check_report")
public class XdcheckReport implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private String memberId;
	private Long pulse;
	private String errorCodes;
	private String writerIds;//
	private String itemFeature;//特征值
	private Date createDate;
	private Integer isSports;

	// Constructors

	/** default constructor */
	public XdcheckReport() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XdcheckReport(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XdcheckReport(Long id, Date createDate) {
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
	public Long getPulse() {
		return pulse;
	}

	public void setPulse(Long pulse) {
		this.pulse = pulse;
	}

	@Column(name = "error_codes")
	public String getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(String errorCodes) {
		this.errorCodes = errorCodes;
	}
	@Column(name = "writer_ids")
	public String getWriterIds() {
		return writerIds;
	}

	public void setWriterIds(String writerIds) {
		this.writerIds = writerIds;
	}
	@Column(name = "item_feature")
	public String getItemFeature() {
		return itemFeature;
	}

	public void setItemFeature(String itemFeature) {
		this.itemFeature = itemFeature;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "is_sports")
	public Integer getIsSports() {
		return isSports;
	}
	public void setIsSports(Integer isSports) {
		this.isSports = isSports;
	}
	
}