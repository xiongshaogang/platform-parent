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
 * 
 */
@Entity
@Table(name = "xx_check_report_apiverage_group")
public class XxcheckReportApiverageGroup implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String memberId;
	private String apiverage24hBloodPressure;//24h平均血压（舒张压/收缩压）
	private String apiverageDaytimeBloodPressure;//日间平均血压（舒张压/收缩压）
	private String apiverageNightBloodPressure;//夜间平均血压（舒张压/收缩压）
	private String daytimeBloodPressureLoadValue;//日间血压负荷值
	private String nightBloodPressureLoadValue;//夜间血压负荷值
	private String nightBloodPressureDeclineRate;//夜间血压下降率
	private String daytimeBloodPressureDifferentCoefficient;//日间血压变异系数
	private String nightBloodPressureDifferentCoefficient;//夜间血压变异系数
	private String bloodPressureDifferentCoefficient;//24h血压变异系数
	private String morningBloodPressure;//晨峰血压
	private String content;//文案
	private Date createDate;

	/** default constructor */
	public XxcheckReportApiverageGroup() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XxcheckReportApiverageGroup(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XxcheckReportApiverageGroup(Long id, Date createDate) {
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
	@Column(name = "apiverage_24h_blood_pressure")
	public String getApiverage24hBloodPressure() {
		return apiverage24hBloodPressure;
	}

	public void setApiverage24hBloodPressure(String apiverage24hBloodPressure) {
		this.apiverage24hBloodPressure = apiverage24hBloodPressure;
	}
	@Column(name = "apiverage_daytime_blood_pressure")
	public String getApiverageDaytimeBloodPressure() {
		return apiverageDaytimeBloodPressure;
	}

	public void setApiverageDaytimeBloodPressure(
			String apiverageDaytimeBloodPressure) {
		this.apiverageDaytimeBloodPressure = apiverageDaytimeBloodPressure;
	}
	@Column(name = "apiverage_night_blood_pressure")
	public String getApiverageNightBloodPressure() {
		return apiverageNightBloodPressure;
	}

	public void setApiverageNightBloodPressure(String apiverageNightBloodPressure) {
		this.apiverageNightBloodPressure = apiverageNightBloodPressure;
	}
	@Column(name = "daytime_blood_pressure_load_value")
	public String getDaytimeBloodPressureLoadValue() {
		return daytimeBloodPressureLoadValue;
	}

	public void setDaytimeBloodPressureLoadValue(
			String daytimeBloodPressureLoadValue) {
		this.daytimeBloodPressureLoadValue = daytimeBloodPressureLoadValue;
	}
	@Column(name = "night_blood_pressure_load_value")
	public String getNightBloodPressureLoadValue() {
		return nightBloodPressureLoadValue;
	}

	public void setNightBloodPressureLoadValue(String nightBloodPressureLoadValue) {
		this.nightBloodPressureLoadValue = nightBloodPressureLoadValue;
	}
	@Column(name = "night_blood_pressure_decline_rate")
	public String getNightBloodPressureDeclineRate() {
		return nightBloodPressureDeclineRate;
	}

	public void setNightBloodPressureDeclineRate(
			String nightBloodPressureDeclineRate) {
		this.nightBloodPressureDeclineRate = nightBloodPressureDeclineRate;
	}
	@Column(name = "daytime_blood_pressure_different_coefficient")
	public String getDaytimeBloodPressureDifferentCoefficient() {
		return daytimeBloodPressureDifferentCoefficient;
	}

	public void setDaytimeBloodPressureDifferentCoefficient(
			String daytimeBloodPressureDifferentCoefficient) {
		this.daytimeBloodPressureDifferentCoefficient = daytimeBloodPressureDifferentCoefficient;
	}
	@Column(name = "night_blood_pressure_different_coefficient")
	public String getNightBloodPressureDifferentCoefficient() {
		return nightBloodPressureDifferentCoefficient;
	}

	public void setNightBloodPressureDifferentCoefficient(
			String nightBloodPressureDifferentCoefficient) {
		this.nightBloodPressureDifferentCoefficient = nightBloodPressureDifferentCoefficient;
	}
	@Column(name = "24h_blood_pressure_different_coefficient")
	public String getBloodPressureDifferentCoefficient() {
		return bloodPressureDifferentCoefficient;
	}

	public void setBloodPressureDifferentCoefficient(
			String bloodPressureDifferentCoefficient) {
		this.bloodPressureDifferentCoefficient = bloodPressureDifferentCoefficient;
	}
	@Column(name = "morning_blood_pressure")
	public String getMorningBloodPressure() {
		return morningBloodPressure;
	}

	public void setMorningBloodPressure(String morningBloodPressure) {
		this.morningBloodPressure = morningBloodPressure;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}