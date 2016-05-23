package com.dvn.telemedicine.dao.x;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.x.XcheckItem;
import com.dvn.telemedicine.entity.x.XcheckReportRefItem;

/**
 * 对象的泛型DAO类.
 * 
 * @author yangzhou
 */
@Component
public class XcheckReportRefItemDao extends HibernateDao<XcheckReportRefItem, String>{
	/**
	 * 
	 * 根据报告Id来获得25项,并且放入相应的定义内容
	 * 
	 * @param reportId
	 * @return
	 */
	public List<XcheckReportRefItem> getListByReportId(Long reportId){
		String hql ="select ri,r from XcheckReportRefItem ri,XcheckItem r " +
				" where ri.itemId = r.id and ri.reportId=?";
		List<Object[]> result= find(hql, reportId);
		List<XcheckReportRefItem> refItemList=new ArrayList<XcheckReportRefItem>();
		for(Object[] obj:result){//index=0是XcheckReportRefItem index 1为XcheckItem
			XcheckReportRefItem refItem=(XcheckReportRefItem)obj[0];
			XcheckItem item=(XcheckItem)obj[1];
			refItem.setItemMark(item.getAbbreviate());
			refItem.setConsultAvgValue(item.getConsultAvgValue());
			refItem.setConsultDeviation(item.getConsultDeviation());
			refItem.setDefine(item.getDefine());
			refItemList.add(refItem);
		}
		return refItemList;
	}
}

