package com.dvn.telemedicine.service.xd;

import org.springside.modules.orm.Page;

import com.dvn.telemedicine.entity.xd.XdcheckReport;

public interface XdcheckReportManager {
	/**
	 * 心电数据入库
	 * @param memberId
	 * @param wh
	 * @return
	 */
	public XdcheckReport saveXdcheckReport(String memberId,String wh,Long pulse,Integer isSports)throws Exception;
	/**
	 * 根据报告id查询对象
	 * @param reportId
	 * @return
	 */
	public XdcheckReport getByReportId(Long reportId);
	/**
	 * 分页查询
	 * @param memberId
	 * @param pager
	 * @param pagerSize
	 * @return
	 */
	public Page<XdcheckReport> getListByMemberId(String memberId,String year,String month,int pager, int pagerSize);
}
