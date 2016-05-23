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
@Table(name = "x_check_report_item_average_day")
public class XcheckReportItemAverageDay implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private String memberId;
	private String itemName;
	private String bloodPressure;
	private String pluse ;
	private String score;
	private Date createDate;

	// Constructors

	/** default constructor */
	public XcheckReportItemAverageDay() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XcheckReportItemAverageDay(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XcheckReportItemAverageDay(Long id, Date createDate) {
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
	@Column(name = "item_name")
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(name = "blood_pressure")
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	@Column(name = "pluse")
	public String getPluse() {
		return pluse;
	}

	public void setPluse(String pluse) {
		this.pluse = pluse;
	}

	
	@Column(name = "score")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
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