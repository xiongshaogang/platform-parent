package com.dvn.telemedicine.service.xd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.xd.XdcheckReportWaveshapeDao;
import com.dvn.telemedicine.entity.xd.XdcheckReportWaveshape;
import com.dvn.telemedicine.service.xd.XdcheckReportWaveshapeManager;

@Component
@Transactional
public class XdcheckReportWaveshapeManagerImpl implements XdcheckReportWaveshapeManager {
	@Autowired
	private XdcheckReportWaveshapeDao xdcheckReportWaveshapeDao;
	@Override
	public void save(XdcheckReportWaveshape wh) {
		xdcheckReportWaveshapeDao.save(wh);
	}
	@Override
	public String getWaveshapeByReportId(Long reportId) {
		String hql="select outWh from XdcheckReportWaveshape where reportId="+reportId;
		List<String> list=xdcheckReportWaveshapeDao.find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}
}
