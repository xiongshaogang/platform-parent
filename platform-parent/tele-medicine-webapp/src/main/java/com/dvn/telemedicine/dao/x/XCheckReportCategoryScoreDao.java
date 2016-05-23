package com.dvn.telemedicine.dao.x;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.x.XCheckReportCategoryScore;

@Component
public class XCheckReportCategoryScoreDao extends HibernateDao<XCheckReportCategoryScore,Long> {
	private final static String ERROR_TIMES_SQL=" select sum(if(s.score>0,1,0)), s.category_id"+
		" from x_check_report_category_score s left join x_check_report r"+ 
		" on s.report_id=r.id "+
		" where r.create_date>=? and r.create_date<? and r.member_id=? "+ 
		" GROUP BY s.category_id";
	
	/**
	 * 
	 * 根据Id获得功能项得分
	 * 
	 * @param reportId
	 * @return
	 */
	public List<XCheckReportCategoryScore> queryCategoryScoresById(Long reportId){
		String hql="from XCheckReportCategoryScore where reportId=?";
		List<XCheckReportCategoryScore> result=find(hql,reportId);
		return result;
		
	}
	public List<Map<String,Integer>> queryErrorTimesByDate(String memberId,Date startTime,Date endTime){
		Session session= getSessionFactory().getCurrentSession();
		Query query=session.createSQLQuery(ERROR_TIMES_SQL);
		query.setDate(0, startTime);
		query.setDate(1,endTime);
		query.setString(2,memberId);
		List<Object[]> objList=query.list();
		List<Map<String,Integer>> result=new ArrayList<Map<String,Integer>>();
		for(Object[] obj:objList){
			Map<String,Integer> info=new HashMap<String, Integer>();
			info.put("times", ((Number)obj[0]).intValue());
			info.put("cateId",(Integer)obj[1]);
			result.add(info);
		}
		return result;
	}
	
}
