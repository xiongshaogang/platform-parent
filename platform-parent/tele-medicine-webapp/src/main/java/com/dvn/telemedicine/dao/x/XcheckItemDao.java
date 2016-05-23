package com.dvn.telemedicine.dao.x;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.dvn.telemedicine.entity.x.XcheckItem;

/**
 * 对象的泛型DAO类.
 * 
 * @author yangzhou
 */
@Component
public class XcheckItemDao extends HibernateDao<XcheckItem, Long>{
}

