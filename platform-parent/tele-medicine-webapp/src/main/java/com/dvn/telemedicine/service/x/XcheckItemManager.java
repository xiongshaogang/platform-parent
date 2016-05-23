package com.dvn.telemedicine.service.x;

import java.util.List;

import com.dvn.telemedicine.entity.x.XcheckItem;

public interface XcheckItemManager {
	/**
	 * 根据类别ID查询24项
	 * @param cataId
	 * @return
	 */
	public List<XcheckItem> getByCategoryId(Long cataId);
	/**
	 * 根据检测项名称查询对象
	 * @param abbreviate
	 * @return
	 */
	public XcheckItem getByAbbreviate(String abbreviate);
	/**
	 * 获取所有
	 * @return
	 */
	public List<XcheckItem> getAllList();
	/**
	 * 根据项名查询
	 * @param itemName
	 * @return
	 */
	public XcheckItem getByItemName(String itemName);
	/**
	 * 获取正在使用中的项
	 * @return
	 */
	public List<XcheckItem> getUseItems();
}
