package cn.com.tcxy.service;

import java.util.List;

import cn.com.tcxy.core.GenericService;
import cn.com.tcxy.core.Pagination;
import cn.com.tcxy.model.Dictionary;
import cn.com.tcxy.model.DictionaryExample;

public interface DictionaryService extends GenericService<Dictionary,DictionaryExample,String>{

	List<Dictionary> selectGender();

    String selectGenderByJson();

    /**
     * 取得会员类型
     */
    List<Dictionary> selectMembertype();

    /**
     * 取得会员等级
     */
    List<Dictionary> selectMemberLevel();

    /**
     * 取得会员注册来源
     */
    List<Dictionary> selectRegisterFrom();

	Dictionary selectResource(String category, String value);

    /**
     * 分页查询字典表
     */
	Pagination queryDictionaryByPage(Dictionary dictionary, Pagination pagination);

    /**
     * 分页查询字典表中所有分类
     */
	Pagination queryCategoryByPage(Dictionary dictionary,Pagination pagination);
	
	int insertDictionary(Dictionary dictionary);

	Dictionary loadCategory(String category);

	int createItemOrderByParentId(String parentId);

	List<Dictionary> queryByParentId(String parentId);

	boolean checkDictionary(Dictionary dictionary, String type);

	/**
	 * @author zhu yi
	 * @param category
	 * @return
	 */
	List<Dictionary> selectResource(String category);
	
	
	List<Dictionary> queryByValues(String category,List<String> values);

    List<Dictionary> selecMallOrderStatus(String type);

    /**
     * @author zhu yi
     * @param category
     * @param value
     * @param text
     * @return
     */
	Dictionary selectResource(String category, String value, String text);
 
}