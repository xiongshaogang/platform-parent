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
@Table(name = "x_check_item")
public class XcheckItem implements java.io.Serializable {

	// Fieldsid

	private Long id;
	private Long categoryId;
	private String abbreviate;
	private String itemName;//
	private String consultAvgValue;
	private String consultDeviation;
	private String weight;//
	private String unit;
	private String gongneng;
	private String coefficient;
	private String modelCoefficient;
	private Integer isUse;
	private String define;
	private Date createDate;

	// Constructors

	/** default constructor */
	public XcheckItem() {
		this.createDate = new Date();
	}

	/** minimal constructor */
	public XcheckItem(Long id) {
		this.id = id;
		this.createDate = new Date();
	}

	/** full constructor */
	public XcheckItem(Long id, Date createDate) {
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
	@Column(name = "category_id")
	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	@Column(name = "abbreviate")
	public String getAbbreviate() {
		return abbreviate;
	}

	public void setAbbreviate(String abbreviate) {
		this.abbreviate = abbreviate;
	}
	@Column(name = "item_name")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(name = "consult_avg_value")
	public String getConsultAvgValue() {
		return consultAvgValue;
	}

	public void setConsultAvgValue(String consultAvgValue) {
		this.consultAvgValue = consultAvgValue;
	}
	@Column(name = "consult_deviation")
	public String getConsultDeviation() {
		return consultDeviation;
	}

	public void setConsultDeviation(String consultDeviation) {
		this.consultDeviation = consultDeviation;
	}
	@Column(name = "weight")
	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}
	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	@Column(name = "coefficient")
	public String getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(String coefficient) {
		this.coefficient = coefficient;
	}
	@Column(name = "model_coefficient")
	public String getModelCoefficient() {
		return modelCoefficient;
	}

	public void setModelCoefficient(String modelCoefficient) {
		this.modelCoefficient = modelCoefficient;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "gongneng")
	public String getGongneng() {
		return gongneng;
	}
	public void setGongneng(String gongneng) {
		this.gongneng = gongneng;
	}
	
	@Column(name = "is_use")
	public Integer getIsUse() {
		return isUse;
	}
	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}
	@Column(name = "define")
	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}
	
}