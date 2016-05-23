package com.dvn.telemedicine.service.x.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;

import com.dvn.telemedicine.dao.x.XcheckReportBloodPressureDao;
import com.dvn.telemedicine.entity.x.XcheckReportBloodPressure;
import com.dvn.telemedicine.service.x.XcheckReportBloodPressureManager;

@Component
@Transactional
public class XcheckReportBloodPressureManagerImpl implements XcheckReportBloodPressureManager {
    
	@Autowired
	private XcheckReportBloodPressureDao xcheckReportBloodPressureDao;

	@Override
	public void save(XcheckReportBloodPressure bp) {
		xcheckReportBloodPressureDao.save(bp);		
	}

	@Override
	public Page<XcheckReportBloodPressure> getListByMemberId(String memberId,
			int pager, int pagerSize) {
		return xcheckReportBloodPressureDao.getListByPage(memberId, pager, pagerSize);
	}

	@Override
	public Page<XcheckReportBloodPressure> getBloodPressureListByDate(String memberId,
			String date, int pager, int pagerSize) {
		Page<XcheckReportBloodPressure> page=new Page<XcheckReportBloodPressure>(pagerSize);
		page.setPageNo(pager);
		String hql="from XcheckReportBloodPressure where memberId='"+memberId+"' and createDate like '%"+date+"%' order by createDate desc";
		return xcheckReportBloodPressureDao.findPage(page, hql);
	}

	@Override
	public void save(List<XcheckReportBloodPressure> plist) {
		for(XcheckReportBloodPressure bp:plist){
			xcheckReportBloodPressureDao.save(bp);		
		}
	}
	
}
