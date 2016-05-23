package com.dvn.telemedicine.service.xd;

import com.dvn.telemedicine.entity.xd.XdcheckReportRefItem;

public interface XdcheckReportRefItemManager {
	/**
	 * 根据报告ID和检测项ID查询对象
	 * @param reportId
	 * @param itemId
	 * @return
	 */
	public XdcheckReportRefItem getByReportIdAndItemId(Long reportId,Long itemId);
}
