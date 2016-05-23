package com.dvn.telemedicine.service.x.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XxcheckReportCrowdDao;
import com.dvn.telemedicine.entity.x.XxcheckReportCrowd;
import com.dvn.telemedicine.service.x.XxcheckReportCrowdManager;

@Component
@Transactional
public class XxcheckReportCrowdManagerImpl implements XxcheckReportCrowdManager {
    
	@Autowired
	private XxcheckReportCrowdDao xxcheckReportCrowdDao;

	@Override
	public XxcheckReportCrowd getByInfo(Long age, Long sex, int bmi) {
		String hql="from XxcheckReportCrowd where ageS<="+age+" and ageE>="+age+" and sex="+sex+" and bmiStrat<="+bmi+" and bmiEnd>="+bmi;
		return xxcheckReportCrowdDao.findUnique(hql);
	}

}
