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
import javax.persistence.Transient;

/**
 * @author Eclipse Persistence Tools
 */
@Entity
@Table(name = "x_check_report")
public class XcheckReport implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private String memberId;
	private Long sex;
	private String pulse;
	private String bloodPressure;//
	private String waveShape;
	private String score;
	private Integer mark;
	private Date createDate;
	private String itemFeature;//特征值
	
	private String memberName;
	private String level;//心脉分值的等级
	// Constructors

	/** default constructor */
	public XcheckReport() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XcheckReport(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XcheckReport(Long id, Date createDate) {
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
	@Column(name = "sex")
	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
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
	@Column(name = "wave_shape")
	public String getWaveShape() {
		return waveShape;
	}

	public void setWaveShape(String waveShape) {
		this.waveShape = waveShape;
	}
	@Column(name = "score")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	@Column(name = "mark")
	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "item_feature")
	public String getItemFeature() {
		return itemFeature;
	}

	public void setItemFeature(String itemFeature) {
		this.itemFeature = itemFeature;
	}

	@Transient
	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	@Transient
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public XcheckReport(Long id, String memberId, Long sex, String pulse,
			String bloodPressure, String score, Integer mark, Date createDate,
			String memberName) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.sex = sex;
		this.pulse = pulse;
		this.bloodPressure = bloodPressure;
		this.score = score;
		this.mark = mark;
		this.createDate = createDate;
		this.memberName = memberName;
	}
	
	
	
}