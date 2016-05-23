package com.dvn.telemedicine.service.x.imp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XcheckReportWaveshapeDao;
import com.dvn.telemedicine.entity.x.XcheckReportWaveshape;
import com.dvn.telemedicine.service.x.XcheckReportWaveshapeManager;

@Component
@Transactional
public class XcheckReportWaveshapeManagerImpl implements XcheckReportWaveshapeManager {
	private static Logger logger = LoggerFactory.getLogger(XcheckReportWaveshapeManagerImpl.class);
	@Autowired
	private XcheckReportWaveshapeDao xcheckReportWaveshapeDao;
	@Override
	public void save(XcheckReportWaveshape wa) {
		xcheckReportWaveshapeDao.save(wa);
	}
	
}
