package com.dvn.telemedicine.service.xd.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.xd.XdcheckItemDao;
import com.dvn.telemedicine.entity.xd.XdcheckItem;
import com.dvn.telemedicine.entity.xd.XdcheckReportRefItem;
import com.dvn.telemedicine.service.xd.XdcheckItemManager;
import com.dvn.telemedicine.service.xd.XdcheckReportRefItemManager;

@Component
@Transactional
public class XdcheckItemManagerImpl implements XdcheckItemManager {
    
	@Autowired
	private XdcheckItemDao xdcheckItemDao;
	@Autowired
	private XdcheckReportRefItemManager xdcheckReportRefItemManager;

	@Override
	public List<XdcheckItem> getAll() {
		String hql="from XdcheckItem order by id asc";
		return xdcheckItemDao.find(hql);
	}

	@Override
	public List<XdcheckItem> getAll(Long reportId) {
		List<XdcheckItem> list=xdcheckItemDao.getAll();
		for(XdcheckItem item:list){
			XdcheckReportRefItem ref=xdcheckReportRefItemManager.getByReportIdAndItemId(reportId, item.getId());
			if(ref!=null){
				item.setTestValue(ref.getTestValue());
			}
		}
		return list;
	}

}
