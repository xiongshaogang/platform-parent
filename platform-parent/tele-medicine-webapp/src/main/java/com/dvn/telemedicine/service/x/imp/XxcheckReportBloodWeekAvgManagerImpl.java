package com.dvn.telemedicine.service.x.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XxcheckReportBloodWeekAvgDao;
import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekAvg;
import com.dvn.telemedicine.service.x.XxcheckReportBloodWeekAvgManager;

@Component
@Transactional
public class XxcheckReportBloodWeekAvgManagerImpl implements XxcheckReportBloodWeekAvgManager {
    
	@Autowired
	private XxcheckReportBloodWeekAvgDao xxcheckReportBloodWeekAvgDao;

	@Override
	public void save(XxcheckReportBloodWeekAvg wavg) {
		xxcheckReportBloodWeekAvgDao.save(wavg);		
	}

	@Override
	public XxcheckReportBloodWeekAvg getByMemberId(String memberId,
			String date, String name) {
		String hql="from XxcheckReportBloodWeekAvg where memberId=? and date=? and time=?";
		return xxcheckReportBloodWeekAvgDao.findUnique(hql, memberId,date,name);
	}

	@Override
	public XxcheckReportBloodWeekAvg getById(Long id) {
		return xxcheckReportBloodWeekAvgDao.findUniqueBy("id", id);
	}

}
