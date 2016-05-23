package com.dvn.telemedicine.service.x.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dvn.telemedicine.core.blood.BloodIndex;
import com.dvn.telemedicine.dao.x.XcheckReportRefItemDao;
import com.dvn.telemedicine.entity.x.XcheckReportRefItem;
import com.dvn.telemedicine.service.x.XcheckReportRefItemManager;

@Component
@Transactional
public class XcheckReportRefItemManagerImpl implements XcheckReportRefItemManager {
    
	@Autowired
	private XcheckReportRefItemDao xcheckReportRefItemDao;

	@Override
	@Transactional(readOnly = true)
	public XcheckReportRefItem getByItemId(Long reportId,Long itemId) {
		String hql="from XcheckReportRefItem where reportId="+reportId+" and itemId="+itemId;
		List<XcheckReportRefItem> list=xcheckReportRefItemDao.find(hql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<XcheckReportRefItem> getListByReportId(Long reportId) {
//		List<XcheckReportRefItem> list=xcheckReportRefItemDao.findBy("reportId", reportId);
//		List<XcheckItem> items= xcheckItemDao.getAll();
//		for(XcheckReportRefItem it:list){
//			XcheckItem em=xcheckItemDao.findUniqueBy("id", it.getItemId());
//			if(em!=null){
//				it.setItemMark(em.getAbbreviate());
//				it.setConsultAvgValue(em.getConsultAvgValue());
//				it.setConsultDeviation(em.getConsultDeviation());
//				it.setDefine(em.getDefine());
//			}
//		}
//以上代码有性能问题,removed by xuwenjie		
		
		List<XcheckReportRefItem> list=xcheckReportRefItemDao.getListByReportId(reportId);
		for(XcheckReportRefItem item:list){
			//结局app端的脉率不是四舍五入bug的修改
			if(BloodIndex.PR.equals(BloodIndex.getBloodIndexById(item.getItemId().intValue()))){
				item.setTestValue(Math.round(Double.valueOf(item.getTestValue()))+"");
			}
		}
		return list;
	}
	

	@Override
	public List<XcheckReportRefItem> getListByDate(Long itemId, List<Long> reportIds) {
		String hql="from XcheckReportRefItem where itemId="+itemId+" and reportId in (";
		for(int i=0;i<reportIds.size();i++){
			if(i==reportIds.size()-1){
				hql+=reportIds.get(i)+")";
			}else{
				hql+=reportIds.get(i)+",";
			}
		}
		return xcheckReportRefItemDao.find(hql);
	}
	
	public void save(XcheckReportRefItem item){
		xcheckReportRefItemDao.save(item);
	}
	
}
