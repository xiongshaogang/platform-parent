package com.dvn.telemedicine.dao;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.MonitorTest;

/**
 * 监控
 * @author tom.chai
 *
 */
@Component
public class MonitorTestDao extends HibernateDao<MonitorTest, Long> {
	
}
