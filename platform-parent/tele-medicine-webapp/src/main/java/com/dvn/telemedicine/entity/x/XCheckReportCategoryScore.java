package com.dvn.telemedicine.entity.x;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * XCheckReportCategoryScore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "x_check_report_category_score")
public class XCheckReportCategoryScore implements java.io.Serializable {

	// Fields

	private Long id;
	private Long reportId;
	private Integer categoryId;
	private Integer score;

	// Constructors

	/** default constructor */
	public XCheckReportCategoryScore() {
	}

	/** full constructor */
	public XCheckReportCategoryScore(Long reportId, Integer categoryId,
			Integer score) {
		this.reportId = reportId;
		this.categoryId = categoryId;
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

	@Column(name = "category_id")
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "score")
	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}