package com.dvn.telemedicine.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dvn.telemedicine.core.cache.CacheProvider;

public class WriterLocalCacheManager implements CacheProvider, InitializingBean {
	private static final Log logger = LogFactory.getLog(WriterLocalCacheManager.class);
	private Cache cache;
	@Override
	public Object get(String key) {
		Element result= cache.get(key);
		if(result==null){
			return null;
		}else{
			logger.info("从本地缓存获得 key ["+key+"] 的文案数据");
			return result.getObjectValue();
		}
	}

	@Override
	public void put(String key, Object obj) {
		cache.put(new Element(key, obj));
		logger.info("放入key ["+key+"]的文案数据到本地缓存");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("local缓存启动");
		
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	
}
