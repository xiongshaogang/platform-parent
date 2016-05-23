package com.dvn.telemedicine.service.x;

import java.util.List;

import com.dvn.telemedicine.entity.x.XxcheckReportApiverageGroup;

public interface XxcheckReportApiverageGroupManager {
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public XxcheckReportApiverageGroup getById(Long id);
	/**
	 * 根据成员和日期（精确到月份）获取该成员有效监测日期
	 * @param memberId
	 * @param date
	 * @return
	 */
	public List<String> getListDate(String memberId,String date);
	/**
	 * 根据成员ID和日期查询平均脉压
	 * @param memberId
	 * @param date
	 * @return
	 */
	public XxcheckReportApiverageGroup getByMemberIdAndDate(String memberId,String date);
}
