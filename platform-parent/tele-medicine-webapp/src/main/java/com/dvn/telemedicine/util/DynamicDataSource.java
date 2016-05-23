package com.dvn.telemedicine.util;

import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{
    
	private static Logger logger = LoggerFactory.getLogger(DynamicDataSource.class);
    @Override  
    public void setTargetDataSources(Map targetDataSources) {  
        super.setTargetDataSources(targetDataSources);  
    }  
    @Override  
    public Object unwrap(Class iface) throws SQLException {  
        return null;  
    }  
    @Override  
    public boolean isWrapperFor(Class iface) throws SQLException {  
        return false;  
    }  
    @Override  
    protected Object determineCurrentLookupKey() {  
        //String dataSourceName = DbContextHolder.getDataSourceName();  
    	String dataSourceName = AppKeys.DATASOURCE1;
//		try {
//			Object mark = MemcachedUtil.get(AppKeys.MEMCACHED_DBMARK);
//			if (mark != null) {
//				if (AppKeys.DATASOURCE1.equals(mark.toString())) {
//					dataSourceName = AppKeys.DATASOURCE1;
//				} else {
//					dataSourceName = AppKeys.DATASOURCE2;
//				}
//			} else {
//				logger.warn("Memcache is not setted,use datasource: "
//						+ dataSourceName);
//			}
//		} catch (Exception e) {
//			logger.warn("Memcache error,use datasource: " + dataSourceName);
//		}
//		logger.debug("dataSource: " + dataSourceName);
        return dataSourceName;  
    }
    
}
