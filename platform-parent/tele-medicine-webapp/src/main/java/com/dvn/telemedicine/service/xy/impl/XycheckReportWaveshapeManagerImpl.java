package com.dvn.telemedicine.service.xy.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.xy.XycheckReportWaveshapeDao;
import com.dvn.telemedicine.entity.xy.XycheckReportWaveshape;
import com.dvn.telemedicine.service.xy.XycheckReportWaveshapeManager;

@Component
@Transactional
public class XycheckReportWaveshapeManagerImpl implements XycheckReportWaveshapeManager {
	@Autowired
	private XycheckReportWaveshapeDao xycheckReportWaveshapeDao;
	@Override
	public void save(XycheckReportWaveshape wh) {
		xycheckReportWaveshapeDao.save(wh);
	}
}
