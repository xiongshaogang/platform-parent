package com.dvn.telemedicine.service.x;

import java.util.List;

import org.springside.modules.orm.Page;

import com.dvn.telemedicine.entity.x.XxcheckReportApiverage;

public interface XxcheckReportApiverageManager {
	
	//新增
	public Long saveReportApiverage(String memberId,String resultJson);
	
	//查询
	public Page<XxcheckReportApiverage>  getReportApiveragePage(String memberId,String startDate,String endDate,Integer pager,Integer pagerSize);
	/**
	 * 获取这一组数据
	 * @param groupId
	 * @return
	 */
	public List<XxcheckReportApiverage> getListByGroupId(Long groupId);
	/**
	 * 生成清晨，夜间，日间的周血压
	 * @param memberId
	 * @param date
	 * @param name
	 * @return
	 */
	public Long getReportApiverageDate(String memberId,List<String> list,String date,String name);

}
