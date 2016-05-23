package com.dvn.telemedicine.dao.xd;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.xd.XdcheckItem;

/**
 * 对象的泛型DAO类.
 * 
 */
@Component
public class XdcheckItemDao extends HibernateDao<XdcheckItem, Long>{
}

