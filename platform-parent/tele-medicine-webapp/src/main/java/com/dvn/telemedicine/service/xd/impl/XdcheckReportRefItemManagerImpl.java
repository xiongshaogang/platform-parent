package com.dvn.telemedicine.service.xd.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.xd.XdcheckReportRefItemDao;
import com.dvn.telemedicine.entity.xd.XdcheckReportRefItem;
import com.dvn.telemedicine.service.xd.XdcheckReportRefItemManager;

@Component
@Transactional
public class XdcheckReportRefItemManagerImpl implements XdcheckReportRefItemManager {
    
	@Autowired
	private XdcheckReportRefItemDao xdcheckReportRefItemDao;

	@Override
	public XdcheckReportRefItem getByReportIdAndItemId(Long reportId,
			Long itemId) {
		String hql="from XdcheckReportRefItem where reportId=? and itemId=?";
		return xdcheckReportRefItemDao.findUnique(hql,reportId,itemId);
	}
	
}
