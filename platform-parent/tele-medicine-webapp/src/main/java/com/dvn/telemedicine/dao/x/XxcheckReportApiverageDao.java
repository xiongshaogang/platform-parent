package com.dvn.telemedicine.dao.x;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.x.XxcheckReportApiverage;

@Component
public class XxcheckReportApiverageDao  extends HibernateDao<XxcheckReportApiverage, Long>{ 
	
	/**
	 * 分页查询
	 * @param pager
	 * @return
	 */
	public Page<XxcheckReportApiverage> getListByPage(String memberId,String startTime,String endTime,Integer pager,Integer pagerSize){
		Page<XxcheckReportApiverage> page = null;
		DetachedCriteria detachedCriteria = null;
		try {
			page = new Page<XxcheckReportApiverage>(pagerSize);
			page.setPageNo(pager);
			detachedCriteria = DetachedCriteria.forClass(XxcheckReportApiverage.class);
			detachedCriteria.add(Restrictions.eq("memberId", memberId));
			if (startTime != null && startTime.length() > 0) {
				startTime += " 00:00:00";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date  date = sdf.parse(startTime);
				detachedCriteria.add(Expression.ge("createDate",new Timestamp(date.getTime())));
			}
			if (endTime != null && endTime.length() > 0) {
				endTime += " 23:59:59";
				SimpleDateFormat sdfe = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date dates = sdfe.parse(startTime);
				detachedCriteria.add(Expression.le("createDate",new Timestamp(dates.getTime())));
			}
			detachedCriteria.addOrder(Order.desc("createDate"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return findByPager(page, detachedCriteria);
	}
		
	@SuppressWarnings("unchecked")
	public Page<XxcheckReportApiverage> findByPager(Page<XxcheckReportApiverage> pager, DetachedCriteria detachedCriteria) {
		Integer pageNumber = pager.getPageNo();
		Integer pageSize = pager.getPageSize();
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		Integer totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		pager.setTotalCount(totalCount);
		pager.setResult(criteria.list());
		return pager;
	}

}
