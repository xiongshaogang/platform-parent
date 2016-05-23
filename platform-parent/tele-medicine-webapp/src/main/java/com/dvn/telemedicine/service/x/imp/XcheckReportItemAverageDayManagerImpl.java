package com.dvn.telemedicine.service.x.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.dao.x.XcheckReportItemAverageDayDao;
import com.dvn.telemedicine.entity.x.XcheckReportItemAverageDay;
import com.dvn.telemedicine.service.x.XcheckReportItemAverageDayManager;

@Component
@Transactional
public class XcheckReportItemAverageDayManagerImpl implements XcheckReportItemAverageDayManager {
    
	@Autowired
	private XcheckReportItemAverageDayDao xcheckReportItemAverageDayDao;


	@Override
	public List<XcheckReportItemAverageDay> getConditionList(String memberId,String itemName,
			String beginDate) {
		String hql="from XcheckReportItemAverageDay where 1=1" ;
		if(memberId !=null){
			hql+=" and memberId ='"+memberId+"'";
		}
		if(itemName !=null){
			hql+=" and itemName ='"+itemName+"'";
		}
		if(beginDate !=null){
			hql+=" and createDate like '%"+beginDate+"%' order by createDate asc";
		}
		if(beginDate ==null){
			hql+=" order by createDate desc";
		}
		
		List<XcheckReportItemAverageDay> list=xcheckReportItemAverageDayDao.find(hql);
		return list;
	}

	@Override
	public void save(XcheckReportItemAverageDay itemAverageDay) {
		xcheckReportItemAverageDayDao.save(itemAverageDay);
	}

	@Override
	public XcheckReportItemAverageDay getByCondition(String memberId,
			String itemName, String beginDate) {
		String hql="from XcheckReportItemAverageDay where 1=1" ;
		if(memberId !=null){
			hql+=" and memberId ='"+memberId+"'";
		}
		if(itemName !=null){
			hql+=" and itemName ='"+itemName+"'";
		}
		if(beginDate !=null){
			hql+=" and createDate like '%"+beginDate+"%' order by createDate asc";
		}
		return xcheckReportItemAverageDayDao.findUnique(hql);
	}
	
	public List<XcheckReportItemAverageDay> getByTime(String date){
		String hql = "from XcheckReportItemAverageDay where id<=166546 and createDate like '"+date+"%' group by memberId";
		return xcheckReportItemAverageDayDao.find(hql);
	}
}
