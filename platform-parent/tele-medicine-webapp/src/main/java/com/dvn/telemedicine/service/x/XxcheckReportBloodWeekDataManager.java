package com.dvn.telemedicine.service.x;

import java.util.List;

import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekData;

public interface XxcheckReportBloodWeekDataManager {
	/**
	 * 添加
	 * @param data
	 */
	public void save(XxcheckReportBloodWeekData data);
	/**
	 * 根据id查询列表
	 * @param weekId
	 * @return
	 */
	public List<XxcheckReportBloodWeekData> getListByWeek(Long weekId);
}
