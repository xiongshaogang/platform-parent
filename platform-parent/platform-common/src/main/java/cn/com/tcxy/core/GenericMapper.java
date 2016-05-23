package cn.com.tcxy.core;

import java.util.List;

/**
 * @author johnny wang
 *
 * @param <T>
 * @param <TE>
 * @param <PK>
 */
public interface GenericMapper<T, TE, PK> {
    int countByExample(TE example);

    int deleteByExample(TE example);

    int deleteByPrimaryKey(PK id);

    int insert(T record);

    int insertSelective(T record);

    List<T> selectByExample(TE example);

    T selectByPrimaryKey(PK id);

    int updateByExampleSelective(T record, TE example);

    int updateByExample(T record, TE example);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

}
