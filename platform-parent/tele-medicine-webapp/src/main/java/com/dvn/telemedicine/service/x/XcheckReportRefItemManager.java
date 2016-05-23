package com.dvn.telemedicine.service.x;

import java.util.List;

import com.dvn.telemedicine.entity.x.XcheckReportRefItem;

public interface XcheckReportRefItemManager {
	/**
	 * 根据报告ID和24项ID查询
	 * @param reportId
	 * @param itemId
	 * @return
	 */
	public XcheckReportRefItem getByItemId(Long reportId,Long itemId);
	/**
	 * 根据报告ID获取24项
	 * @param reportId
	 * @return
	 */
	public List<XcheckReportRefItem> getListByReportId(Long reportId);
	/**
	 * 根据日期查询项列表
	 * @param itemId
	 * @param date
	 * @return
	 */
	public List<XcheckReportRefItem> getListByDate(Long itemId,List<Long> reportIds);
}
