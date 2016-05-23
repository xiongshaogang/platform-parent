package cn.com.tcxy.cache;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.test.BaseSpringTest;

public class CacheServiceTest extends BaseSpringTest {
    private static Logger LOGGER = LoggerFactory
            .getLogger(CacheServiceTest.class);

    @Autowired
    private CacheService cacheService;

    @Test
    public void get() {
        Object obj = cacheService.get("key1");
        LOGGER.info("obj:" + obj);
    }

    @Test
    public void set() {
        Object obj = cacheService.set("key1", "1");
        LOGGER.info("obj:" + obj);
    }

}
