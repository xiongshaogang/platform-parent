package com.dvn.telemedicine.service.xd;

import com.dvn.telemedicine.entity.xd.XdcheckReportWaveshape;

public interface XdcheckReportWaveshapeManager {
	/**
	 * 添加
	 * @param wh
	 */
	public void save(XdcheckReportWaveshape wh);
	/**
	 * 根据报告ID查询输出波形
	 * @param reportId
	 * @return
	 */
	public String getWaveshapeByReportId(Long reportId);
}
