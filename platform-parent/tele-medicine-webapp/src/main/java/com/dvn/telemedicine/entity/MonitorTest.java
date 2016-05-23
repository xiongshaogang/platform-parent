package com.dvn.telemedicine.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 监控
 * @author tom.chai
 */
@Entity
@Table(name = "monitor_test")
public class MonitorTest extends IdEntity {
	
	private String monitorValue;
	

	public String getMonitorValue() {
		return monitorValue;
	}

	public void setMonitorValue(String monitorValue) {
		this.monitorValue = monitorValue;
	}
	
}
