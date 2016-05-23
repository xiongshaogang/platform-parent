package com.dvn.telemedicine.dao.x;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.x.XcheckReport;
import com.dvn.telemedicine.entity.x.XcheckReportRefItem;

/**
 * 对象的泛型DAO类.
 * 
 * @author yangzhou
 */
@Component
public class XcheckReportDao extends HibernateDao<XcheckReport, Long>{
	/**
	 * 根据Pager和DetachedCriteria对象进行查询(提供分页、查找、排序功能).
	 * @param pager
	 * @param detachedCriteria
	 * 根据Id分页
	 * @return
	 */
	public List<XcheckReport> getList(String memberId,Integer mark,Integer pager,Integer pagerSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(XcheckReport.class);
		dc.add(Restrictions.eq("memberId", memberId));
		dc.add(Restrictions.eq("mark", mark));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pager - 1) * pagerSize);
		criteria.setMaxResults(pagerSize);
		List<XcheckReport> list=criteria.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<XcheckReport> getList(String memberId,Integer pager,Integer pagerSize) {
		DetachedCriteria dc = DetachedCriteria.forClass(XcheckReport.class);
		Calendar c=Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, -30);
		Date date=c.getTime();
		dc.add(Restrictions.eq("memberId", memberId));
		dc.add(Restrictions.ge("createDate", date));
		Criteria criteria = dc.getExecutableCriteria(getSession());
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pager - 1) * pagerSize);
		criteria.setMaxResults(pagerSize);
		List<XcheckReport> list=criteria.list();
		return list;
	}
	
	/**
	 * * 根据Pager对象进行查询(提供分页、查找、排序功能).
	 * 
	 * @param pager
	 * @return
	 */
	public Page<XcheckReport> getListByPage(String memberId,Integer mark,Integer pager,Integer pagerSize){
		Page<XcheckReport> page=new Page<XcheckReport>(pagerSize);
		page.setPageNo(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(XcheckReport.class);
		detachedCriteria.add(Restrictions.eq("memberId", memberId));
		detachedCriteria.add(Restrictions.eq("mark", mark));
		detachedCriteria.addOrder(Order.desc("createDate"));
		return findByPager(page, detachedCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public Page<XcheckReport> findByPager(Page<XcheckReport> pager, DetachedCriteria detachedCriteria) {
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
	
	
	/**
	 * 根据Pager对象进行查询 按年月日
	 * @param pager
	 * @return
	 */
	public Page<XcheckReport> getListByPageByDate(String memberId,Integer mark,String year,String month,Integer pager,Integer pagerSize){
		Page<XcheckReport> page=new Page<XcheckReport>(pagerSize);
		page.setPageNo(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(XcheckReport.class);
		detachedCriteria.add(Restrictions.eq("memberId", memberId));
		detachedCriteria.add(Restrictions.eq("mark", mark));
		
		detachedCriteria.add(Restrictions.sqlRestriction("year(create_date) = '"+year+"'"));
		detachedCriteria.add(Restrictions.sqlRestriction("month(create_date) = '"+month+"'"));
		//detachedCriteria.add(Restrictions.sqlRestriction("create_date like '%"+date+"%'"));
		
		detachedCriteria.addOrder(Order.desc("createDate"));
		return findByPager(page, detachedCriteria);
	}
	
	/**
	 * 根据Pager报告对象进行查询 具体字段
	 * @param pager
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page getListByPageByTime(String memberId,Integer mark,String itemName,Integer pageNo,Integer pagerSize){
		Page<XcheckReport> page=new Page<XcheckReport>(pagerSize);
		page.setPageNo(pageNo);
		String hql="select ";
		if(itemName!=null && !("").equals(itemName)){
			if(itemName.equals("score")){
				hql+="id,score,bloodPressure,pulse,createDate";
			}else if(itemName.equals("bloodPressure")){
				hql+="bloodPressure,createDate";
			}else{
				hql+=itemName;
			}
			hql+=" from XcheckReport where memberId='"+memberId+"' and mark="+mark+" order by createDate desc";
		}
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(XcheckReport.class);
//		detachedCriteria.add(Restrictions.eq("memberId", memberId));
//		detachedCriteria.add(Restrictions.eq("mark", mark));
//		detachedCriteria.addOrder(Order.desc("createDate"));
//		
//		Integer pageNumber = page.getPageNo();
//		Integer pageSize = page.getPageSize();
//		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
//		Integer totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
//		//criteria.setProjection(null);
//		//
//		ProjectionList projectList = Projections.projectionList();//select 部分
//	    projectList.add(Projections.property(itemName));//之查询ID
//	    projectList.add(Projections.property("createDate"));//之查询ID
//	    criteria.setProjection(projectList);
//	    criteria.setResultTransformer(Transformers.aliasToBean(XcheckReport.class));
//	    //
//		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
//		criteria.setFirstResult((pageNumber - 1) * pageSize);
//		criteria.setMaxResults(pageSize);
//		
//	    page.setTotalCount(totalCount);
//		page.setResult(criteria.list());
		return findPage(page, hql);
	}
	
	
	/**
	 * 根据Pager报告检测项对象进行查询 具体字段
	 * @param pager
	 * @return
	 */
	public Page newGetMemberItemRecordByTime(String memberId,Long itemMark,Integer testMark,Integer pager,Integer pagerSize) {
		Page<Object> page=new Page<Object>(pagerSize);
		page.setPageNo(pager);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(XcheckReportRefItem.class);
		
		//查询所有当前会员的ID
		Page pages=getListByPageByTime(memberId,testMark,"id",pager,pagerSize);
		detachedCriteria.add(Restrictions.in("reportId",pages.getResult().toArray()));
		detachedCriteria.add(Restrictions.eq("itemId", itemMark));

		Integer pageNumber = page.getPageNo();
		Integer pageSize = page.getPageSize();
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		Object obj = criteria.setProjection(Projections.rowCount()).uniqueResult();
		Integer totalCount = Integer.parseInt(criteria.setProjection(Projections.rowCount()).uniqueResult().toString());
		//criteria.setProjection(null);
		//
		ProjectionList projectList = Projections.projectionList();//select 部分
	    projectList.add(Projections.property("testValue"));//只查询testValue
	    criteria.setProjection(projectList);
	    criteria.setResultTransformer(Transformers.aliasToBean(XcheckReportRefItem.class));
	    //
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		criteria.setFirstResult((pageNumber - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		
		page.setTotalCount(totalCount);
		page.setResult(criteria.list());
		
		return page;
		
	}
	
	/**
	 * 
	 * 根据时间范围获取检测报告的数量
	 * 
	 * @param memberId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getCountOfTestTimes(String memberId,Date startDate,Date endDate){
		String hql=" select count(id) from XcheckReport where memberId=? and createDate>=? and createDate<=?";
		Query query= createQuery(hql, memberId,startDate,endDate);
		return ((Number)query.uniqueResult()).intValue();
	}
	
	/**
	 * 
	 * 获得上一条记录的reportId
	 * @param memberId
	 * @return
	 */
	public Long getPreviousReportId(String memberId,Long reportId){
		String hql="select id from XcheckReport where memberId=? and id<>? order by createDate desc";
		Query query= createQuery(hql, memberId,reportId);
		query.setMaxResults(1);
		List<Long> result= query.list();
		if(!result.isEmpty()){
			return result.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	 * 取得测试的天数
	 * 
	 * @param memberId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getTestDay(String memberId,Date startDate,Date endDate){
		Session session= getSessionFactory().getCurrentSession();
		Query query=session.createSQLQuery("select COUNT(DISTINCT CONCAT(YEAR(create_date),MONTH(create_date),DAY(create_date)) ) from x_check_report " +
				" where create_date>=? and create_date<? and member_id=?");
		query.setDate(0,startDate).setDate(1, endDate).setString(2,memberId);
		return((Number)query.uniqueResult()).intValue();
	}
	
}

