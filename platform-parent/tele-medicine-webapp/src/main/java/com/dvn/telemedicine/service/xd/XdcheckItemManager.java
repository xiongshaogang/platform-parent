package com.dvn.telemedicine.service.xd;

import java.util.List;

import com.dvn.telemedicine.entity.xd.XdcheckItem;

public interface XdcheckItemManager {
	/**
	 * 获取所有
	 * @return
	 */
	public List<XdcheckItem> getAll();
	/**
	 * 根据报告ID查询检测项
	 * @param reportId
	 * @return
	 */
	public List<XdcheckItem> getAll(Long reportId);
}
