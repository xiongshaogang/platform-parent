package com.dvn.telemedicine.service.x.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XxcheckReportBloodWeekDataDao;
import com.dvn.telemedicine.entity.x.XxcheckReportBloodWeekData;
import com.dvn.telemedicine.service.x.XxcheckReportBloodWeekDataManager;

@Component
@Transactional
public class XxcheckReportBloodWeekDataManagerImpl implements XxcheckReportBloodWeekDataManager {
    
	@Autowired
	private XxcheckReportBloodWeekDataDao xxcheckReportBloodWeekDataDao;

	@Override
	public void save(XxcheckReportBloodWeekData data) {
		xxcheckReportBloodWeekDataDao.save(data);		
	}

	@Override
	public List<XxcheckReportBloodWeekData> getListByWeek(Long weekId) {
		String hql="from XxcheckReportBloodWeekData where weekId="+weekId+" order by date desc";
		return xxcheckReportBloodWeekDataDao.find(hql);
	}

}
