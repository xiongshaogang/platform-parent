package com.dvn.telemedicine.service.x;

import java.util.List;

import org.springside.modules.orm.Page;

import com.dvn.telemedicine.entity.x.XcheckReportBloodPressure;

public interface XcheckReportBloodPressureManager {
	/**
	 * 添加
	 * @param bp
	 */
	public void save(XcheckReportBloodPressure bp);
	/**
	 * 批量添加
	 * @param plist
	 */
	public void save(List<XcheckReportBloodPressure> plist);
	/**
	 * 查询成员的历史记录
	 * @param memberId
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public Page<XcheckReportBloodPressure> getListByMemberId(String memberId,int pager,int pagerSize);
	/**
	 * 按月 查询成员的历史记录
	 * @param memberId
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public Page<XcheckReportBloodPressure> getBloodPressureListByDate(String memberId,String date,int pager,int pagerSize);
}
