package com.dvn.telemedicine.service.x;

import java.util.List;

import com.dvn.telemedicine.entity.x.XcheckReportItemAverageDay;

public interface XcheckReportItemAverageDayManager {
	/**
	 * 根据成员ID与时间节点查询
	 * @param memberId
	 * @return
	 */
	public List<XcheckReportItemAverageDay> getConditionList(String memberId,String itemName,String beginDate);
	
	/**
	 * 添加
	 * @param itemAverageDay
	 */
	public void save(XcheckReportItemAverageDay itemAverageDay);
	
	/**
	 * 根据成员ID,时间,itemName查询
	 * @param memberId
	 * @param itemName
	 * @param beginDate
	 * @return
	 */
	public XcheckReportItemAverageDay getByCondition(String memberId,String itemName,String beginDate);
	
}
