package com.dvn.telemedicine.service.x.imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XxcheckReportApiverageGroupDao;
import com.dvn.telemedicine.entity.x.XxcheckReportApiverageGroup;
import com.dvn.telemedicine.service.x.XxcheckReportApiverageGroupManager;

@Component
@Transactional
public class XxcheckReportApiverageGroupManagerImpl implements XxcheckReportApiverageGroupManager {
	
	private static Logger logger = LoggerFactory.getLogger(XxcheckReportApiverageGroupManagerImpl.class);
	@Autowired
	private XxcheckReportApiverageGroupDao xxcheckReportApiverageGroupDao;
	@Override
	public XxcheckReportApiverageGroup getById(Long id) {
		return xxcheckReportApiverageGroupDao.findUniqueBy("id", id);
	}
	@Override
	public List<String> getListDate(String memberId, String date) {
		String hql="select createDate from XxcheckReportApiverageGroup where memberId='"+memberId+"' and createDate like '"+date+"%' order by createDate desc";
		return xxcheckReportApiverageGroupDao.find(hql);
	}
	@Override
	public XxcheckReportApiverageGroup getByMemberIdAndDate(String memberId,
			String date) {
		String hql="from XxcheckReportApiverageGroup where memberId=? and createDate like '"+date+"%'";
		return xxcheckReportApiverageGroupDao.findUnique(hql, memberId);
	}
}
