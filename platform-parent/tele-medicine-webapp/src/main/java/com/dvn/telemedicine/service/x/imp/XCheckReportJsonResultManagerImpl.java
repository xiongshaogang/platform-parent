package com.dvn.telemedicine.service.x.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dvn.telemedicine.dao.x.XCheckReportJsonResultDao;
import com.dvn.telemedicine.entity.x.XCheckReportJsonResult;
import com.dvn.telemedicine.service.x.XCheckReportJsonResultManager;

@Component
public class XCheckReportJsonResultManagerImpl implements XCheckReportJsonResultManager{
	@Autowired
	private XCheckReportJsonResultDao xCheckReportJsonResultDao;
	
	@Override
	public String getResultById(Long reportId) {
	 	XCheckReportJsonResult result= xCheckReportJsonResultDao.findUniqueBy("reportId", reportId);
	 	if(result==null){
	 		return null;
	 	}else{
	 		return result.getJsonData();
	 	}
	}
	
}
