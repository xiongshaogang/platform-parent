package com.dvn.telemedicine.entity.x;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Eclipse Persistence Tools
 */
@Entity
@Table(name = "xx_check_report_crowd")
public class XxcheckReportCrowd implements java.io.Serializable {

	// Fieldsid
	private Long id;
	private Long ageS;//
	private Long ageE;//
	private Long sex;//
	private Long bmiStrat;//
	private Long bmiEnd;//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name = "age_s")
	public Long getAgeS() {
		return ageS;
	}

	public void setAgeS(Long ageS) {
		this.ageS = ageS;
	}
	@Column(name = "age_e")
	public Long getAgeE() {
		return ageE;
	}

	public void setAgeE(Long ageE) {
		this.ageE = ageE;
	}
	@Column(name = "sex")
	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}
	@Column(name = "bmi_strat")
	public Long getBmiStrat() {
		return bmiStrat;
	}

	public void setBmiStrat(Long bmiStrat) {
		this.bmiStrat = bmiStrat;
	}
	@Column(name = "bmi_end")
	public Long getBmiEnd() {
		return bmiEnd;
	}

	public void setBmiEnd(Long bmiEnd) {
		this.bmiEnd = bmiEnd;
	}
	
	
}