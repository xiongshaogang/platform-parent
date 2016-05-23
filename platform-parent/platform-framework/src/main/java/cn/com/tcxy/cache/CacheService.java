package cn.com.tcxy.cache;

import java.util.Date;

/**
 * @author johnny wang
 * 
 */
public interface CacheService {

    Object get(String key);

    boolean set(String key, Object obj);

    boolean set(String key, Object obj, Date expireDate);

    boolean delete(String key);

    boolean exists(String key);
}
