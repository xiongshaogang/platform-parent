package com.dvn.telemedicine.dao.xy;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.xy.XycheckReport;

/**
 * 对象的泛型DAO类.
 * 
 */
@Component
public class XycheckReportDao extends HibernateDao<XycheckReport, Long>{
	/**
	 * * 根据Pager对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 * @return
	 */
	public Page<XycheckReport> getListByPage(String memberId,String year,String month,int pager,int pagerSize){
		Page<XycheckReport> page=new Page<XycheckReport>(pagerSize);
		page.setPageNo(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(XycheckReport.class);
		detachedCriteria.add(Restrictions.eq("memberId", memberId));
		detachedCriteria.add(Restrictions.sqlRestriction("year(create_date) = '"+year+"'"));
		detachedCriteria.add(Restrictions.sqlRestriction("month(create_date) = '"+month+"'"));
		detachedCriteria.addOrder(Order.desc("createDate"));
		return findByPager(page, detachedCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public Page<XycheckReport> findByPager(Page<XycheckReport> pager, DetachedCriteria detachedCriteria) {
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

