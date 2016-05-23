package com.dvn.telemedicine.service.xy.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.dvn.telemedicine.core.TelemedicineAnalyzer;
import com.dvn.telemedicine.core.bloodoxygen.BloodOxygenRequestData;
import com.dvn.telemedicine.core.bloodoxygen.BloodOxygenResult;
import com.dvn.telemedicine.dao.xy.XycheckReportDao;
import com.dvn.telemedicine.dao.xy.XycheckReportWaveshapeDao;
import com.dvn.telemedicine.entity.xy.XycheckReport;
import com.dvn.telemedicine.entity.xy.XycheckReportWaveshape;
import com.dvn.telemedicine.service.xy.XycheckReportManager;

@Component
@Transactional
public class XycheckReportManagerImpl implements XycheckReportManager {
    
	@Autowired
	private XycheckReportDao xycheckReportDao;
	@Autowired
	private XycheckReportWaveshapeDao xycheckReportWaveshapeDao;
	@Autowired
	private TelemedicineAnalyzer telemedicineAnalyzer;

	@Override
	public XycheckReport save(String memberId,String testValue,String wh) {
		BloodOxygenRequestData requestData=new BloodOxygenRequestData();
		requestData.setTestValue(testValue);
		BloodOxygenResult result= telemedicineAnalyzer.analyzeBloodOxygen(requestData);//利用脉诊类获得结果
		XycheckReport rep=new XycheckReport();
		rep.setMemberId(memberId);
		rep.setTestValue(testValue);
		rep.setLevel(result.getLevel());
		rep.setCreateDate(new Date());
		xycheckReportDao.save(rep);
		
		XycheckReportWaveshape w=new XycheckReportWaveshape();
		w.setReportId(rep.getId());
		w.setWaveShape(wh);
		w.setCreateDate(new Date());
		xycheckReportWaveshapeDao.save(w);
		return rep;
	}
	@Override
	public Page<XycheckReport> getListByMemberId(String memberId,String year,String month,
			int pager, int pagerSize) {
		return xycheckReportDao.getListByPage(memberId, year,month,pager, pagerSize);
	}
}
