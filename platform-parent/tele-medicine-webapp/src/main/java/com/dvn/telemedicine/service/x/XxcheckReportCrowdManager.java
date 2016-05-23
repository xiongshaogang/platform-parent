package com.dvn.telemedicine.service.x;

import com.dvn.telemedicine.entity.x.XxcheckReportCrowd;

public interface XxcheckReportCrowdManager {
	/**
	 * 根据年龄，性别，bmi查询
	 * @param age
	 * @param sex
	 * @param bmi
	 * @return
	 */
	public XxcheckReportCrowd getByInfo(Long age,Long sex,int bmi);
}
