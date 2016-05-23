package com.dvn.telemedicine.entity.x;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * XCheckReportModelScore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "x_check_report_model_score")
public class XCheckReportModelScore implements java.io.Serializable {

	// Fields

	private Long id;
	private Long reportId;
	private Integer modelId;
	private Integer score;

	// Constructors

	/** default constructor */
	public XCheckReportModelScore() {
	}

	/** full constructor */
	public XCheckReportModelScore(Long reportId, Integer modelId, Integer score) {
		this.reportId = reportId;
		this.modelId = modelId;
		this.score = score;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "report_id")
	public Long getReportId() {
		return this.reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	@Column(name = "model_id")
	public Integer getModelId() {
		return this.modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	@Column(name = "score")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}