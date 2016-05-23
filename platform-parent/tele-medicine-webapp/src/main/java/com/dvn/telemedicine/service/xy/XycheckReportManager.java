package com.dvn.telemedicine.service.xy;

import org.springside.modules.orm.Page;

import com.dvn.telemedicine.entity.xy.XycheckReport;

public interface XycheckReportManager {
	/**
	 * 添加
	 * @param rep
	 */
	public XycheckReport save(String memberId,String testValue,String wh);
	/**
	 * 分页查询
	 * @param memberId
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public Page<XycheckReport> getListByMemberId(String memberId,String year,String month,int pager, int pagerSize);
}
