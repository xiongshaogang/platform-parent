package com.dvn.telemedicine.service.x;

import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekAvg;

public interface XxcheckReportBloodWeekAvgManager {
	/**
	 * 添加
	 * @param wavg
	 */
	public void save(XxcheckReportBloodWeekAvg wavg);
	/**
	 * 根据成员ID，时间周，时间段查询
	 * @param memberId
	 * @param date
	 * @param name
	 * @return
	 */
	public XxcheckReportBloodWeekAvg getByMemberId(String memberId,String date,String name);
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public XxcheckReportBloodWeekAvg getById(Long id);
}
