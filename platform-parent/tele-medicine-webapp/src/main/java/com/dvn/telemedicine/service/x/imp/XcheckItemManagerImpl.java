package com.dvn.telemedicine.service.x.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XcheckItemDao;
import com.dvn.telemedicine.entity.x.XcheckItem;
import com.dvn.telemedicine.service.x.XcheckItemManager;

@Component
@Transactional
public class XcheckItemManagerImpl implements XcheckItemManager {
    
	@Autowired
	private XcheckItemDao xcheckItemDao;

	@Override
	@Transactional(readOnly = true)
	public List<XcheckItem> getByCategoryId(Long cataId) {
		return xcheckItemDao.findBy("categoryId", cataId);
	}

	@Override
	public XcheckItem getByAbbreviate(String abbreviate) {
		return xcheckItemDao.findUniqueBy("abbreviate", abbreviate);
	}

	@Override
	@Transactional(readOnly = true)
	public List<XcheckItem> getAllList() {
		return xcheckItemDao.getAll();
	}

	@Override
	public XcheckItem getByItemName(String itemName) {
		return xcheckItemDao.findUniqueBy("itemName", itemName);
	}

	@Override
	public List<XcheckItem> getUseItems() {
		String hql="from XcheckItem where isUse=1";
		return xcheckItemDao.find(hql);
	}
}
