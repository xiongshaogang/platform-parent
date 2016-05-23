package com.dvn.telemedicine.entity.x;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * @author Eclipse Persistence Tools
 */
@Entity
@Table(name = "x_check_report_ref_item")
public class XcheckReportRefItem implements java.io.Serializable {

	// Fieldsid

	private String id;
	private Long itemId;
	private Long reportId;
	private String testValue;//
	private Date createDate;
	
	private String itemMark;
	private String consultAvgValue;
	private String consultDeviation;
	private String define;
	// Constructors

	/** default constructor */
	public XcheckReportRefItem() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XcheckReportRefItem(String id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XcheckReportRefItem(String id, Date createDate) {
		this.id = id;
		this.createDate = createDate;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
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
//		if(testValue!=null && !("").equals(testValue)){
//			if(Integer.parseInt(itemId+"")==1){
//				Double s=Double.valueOf(testValue);
//				int ss=new BigDecimal(s).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();//四舍五入
//				return ss+"";
//			}
//		}
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
	@Transient
	public String getItemMark() {
		return itemMark;
	}

	public void setItemMark(String itemMark) {
		this.itemMark = itemMark;
	}
	@Transient
	public String getConsultAvgValue() {
		return consultAvgValue;
	}

	public void setConsultAvgValue(String consultAvgValue) {
		this.consultAvgValue = consultAvgValue;
	}
	@Transient
	public String getConsultDeviation() {
		return consultDeviation;
	}

	public void setConsultDeviation(String consultDeviation) {
		this.consultDeviation = consultDeviation;
	}
	@Transient
	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}
	
}