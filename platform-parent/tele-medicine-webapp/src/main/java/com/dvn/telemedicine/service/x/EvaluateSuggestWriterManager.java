package com.dvn.telemedicine.service.x;

import java.util.Map;

import com.dvn.telemedicine.entity.x.Xmember;

public interface EvaluateSuggestWriterManager {

	/**
	 * 根据报告ID查询文案
	 * @param cateMark
	 * @return
	 */
	public  Map<String,String> searchWriter(Xmember member,Long reportId,Double pr);
}
