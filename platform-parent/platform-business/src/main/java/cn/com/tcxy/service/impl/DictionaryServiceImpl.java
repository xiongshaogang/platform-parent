package cn.com.tcxy.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.tcxy.core.GenericMapper;
import cn.com.tcxy.core.GenericServiceImpl;
import cn.com.tcxy.core.Pagination;
import cn.com.tcxy.mapper.DictionaryMapper;
import cn.com.tcxy.model.Dictionary;
import cn.com.tcxy.model.DictionaryExample;
import cn.com.tcxy.model.base.BaseDictionaryExample.Criteria;
import cn.com.tcxy.service.DictionaryService;
import cn.com.tcxy.util.StringUtil;
import cn.com.tcxy.util.UUIDUtil;

import com.alibaba.fastjson.JSON;

@Service("dictionaryService")
public class DictionaryServiceImpl extends GenericServiceImpl<Dictionary,DictionaryExample,String> implements DictionaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryServiceImpl.class);

    @Autowired
    private DictionaryMapper dictionaryMapper;
    
    @Override
    public Pagination queryDictionaryByPage(Dictionary dictionary,Pagination pagination) {
        DictionaryExample ex = new DictionaryExample();
        Criteria c1 =  ex.createCriteria();
        if(!StringUtil.isEmpty(dictionary.getCategory())){
        	c1.andCategoryEqualTo(dictionary.getCategory());
        }
        if(!StringUtil.isEmpty(dictionary.getValue())){
        	c1.andValueLike("%"+dictionary.getValue()+"%");
        }
        if(!StringUtil.isEmpty(dictionary.getText())){
        	c1.andTextLike("%"+dictionary.getText()+"%");
        }
        c1.andValueIsNotNull();
        ex.setPagination(pagination);
        ex.setOrderByClause("item_order");
        
        List<Dictionary> dicList = this.dictionaryMapper.selectByExample(ex);
        int totalRowNumber = this.dictionaryMapper.countByExample(ex);
        pagination.setDataList(dicList);
        pagination.setTotalRowNumber(totalRowNumber);
        return pagination;
    }  
    
    @Override
    public Pagination queryCategoryByPage(Dictionary dictionary,Pagination pagination) {
        DictionaryExample ex = new DictionaryExample();
        Criteria c1 =  ex.createCriteria();
        if(!StringUtil.isEmpty(dictionary.getCategory())){
        	c1.andCategoryLike("%"+dictionary.getCategory()+"%");
        }
        if(!StringUtil.isEmpty(dictionary.getText())){
        	c1.andTextLike("%"+dictionary.getText()+"%");
        }
        c1.andValueIsNull();
        c1.andParentIdIsNull();
        ex.setPagination(pagination);
        //ex.setOrderByClause("item_order");
        
        List<Dictionary> dicList = this.dictionaryMapper.selectByExample(ex);
        int totalRowNumber = this.dictionaryMapper.countByExample(ex);
        pagination.setDataList(dicList);
        pagination.setTotalRowNumber(totalRowNumber);
        return pagination;
    }  
    
    @Override
    public int insertDictionary(Dictionary dictionary) {
    	dictionary.setId(UUIDUtil.getUUID());
    	dictionary.setValid(true);
    	return this.dictionaryMapper.insert(dictionary);
    }
    
    @Override
    public List<Dictionary> selectResource(String category) {
        DictionaryExample ex = new DictionaryExample();
        ex.createCriteria()
        .andCategoryEqualTo(category)
        .andValueIsNotNull();
        ex.setOrderByClause("item_order");
        return this.dictionaryMapper.selectByExample(ex);
    }
    
    @Override
    public List<Dictionary> selecMallOrderStatus(String type) {
        DictionaryExample ex = new DictionaryExample();
        ex.createCriteria().andCategoryEqualTo("mall_order_status").andValueIsNotNull();
        ex.setOrderByClause("item_order");
        List<Dictionary> dicList1 = this.dictionaryMapper.selectByExample(ex);
        List<Dictionary> dicList2=new ArrayList<Dictionary>();
        for(Dictionary dic:dicList1){
            dicList2.add(dic);
            if (StringUtils.isNotBlank(type) && type.equals("product")&&dic.getValue().endsWith("service")) {
                dicList2.remove(dic);
            }
            if (StringUtils.isNotBlank(type) && type.equals("service")&&dic.getValue().endsWith("product")) {
                dicList2.remove(dic);
            }
        }
        return dicList2;
        
    }
    
    @Override
    public Dictionary loadCategory(String category) {
        DictionaryExample ex = new DictionaryExample();
        ex.createCriteria()
        .andCategoryEqualTo(category)
        .andValueIsNull();
        List<Dictionary> result = this.dictionaryMapper.selectByExample(ex);
        return result.isEmpty()?new Dictionary():result.get(0);
    }
    
    @Override
    public Dictionary selectResource(String category,String value) {
        if(StringUtil.isEmpty(category,value)){
            return null;
        }
    	DictionaryExample ex = new DictionaryExample();
    	ex.createCriteria()
    	.andCategoryEqualTo(category)
    	.andValueEqualTo(value);
    	
    	ex.setOrderByClause("item_order");
    	List<Dictionary> result = this.dictionaryMapper.selectByExample(ex);
    	return result.isEmpty()?new Dictionary():result.get(0);    	
    }
    
    @Override
    public Dictionary selectResource(String category,String value,String text) {
        if(StringUtil.isEmpty(category)){
            return null;
        }
        DictionaryExample ex = new DictionaryExample();
        Criteria c1 = ex.createCriteria();
        c1.andCategoryEqualTo(category);
        
        if(!StringUtil.isEmpty(value)){
            c1.andValueEqualTo(value);
        }
        if(!StringUtil.isEmpty(text)){
            c1.andTextEqualTo(text);
        }
        
        List<Dictionary> result = this.dictionaryMapper.selectByExample(ex);
        return result.isEmpty()?null:result.get(0);
    }
    
    @Override
    public boolean checkDictionary(Dictionary dictionary,String type) {
    	DictionaryExample ex = new DictionaryExample();
    	Criteria c1 = ex.createCriteria();
    	if(!StringUtil.isEmpty(dictionary.getCategory())){
        	c1.andCategoryEqualTo(dictionary.getCategory());
        }
    	if(!StringUtil.isEmpty(dictionary.getParentId())){
    		c1.andParentIdEqualTo(dictionary.getParentId());
    	}
    	if(!StringUtil.isEmpty(dictionary.getText())){
    		c1.andTextEqualTo(dictionary.getText());
    	}
    	if(!StringUtil.isEmpty(dictionary.getTextEn())){
            c1.andTextEnEqualTo(dictionary.getTextEn());
        }
    	if(!StringUtil.isEmpty(dictionary.getValue())){
    		c1.andValueEqualTo(dictionary.getValue());
    	}
    	if(!("dictionary").equals(type)){
    		c1.andValueIsNull();
    		c1.andParentIdIsNull();
    	}
    	List<Dictionary> result = this.dictionaryMapper.selectByExample(ex);
    	return result.isEmpty()?true:false; 	
    }
  
    @Override
    public int createItemOrderByParentId(String parentId) {
    	DictionaryExample ex = new DictionaryExample();
    	ex.createCriteria().andParentIdEqualTo(parentId);
    	ex.setOrderByClause("item_order desc");
    	List<Dictionary> result = this.dictionaryMapper.selectByExample(ex);
    	if(result.isEmpty()){
    		return 1;
    	}else{
    		return result.get(0).getItemOrder()+1;
    	} 	
    }
    
    @Override
    public List<Dictionary> queryByParentId(String parentId) {
    	DictionaryExample ex = new DictionaryExample();
    	ex.createCriteria().andParentIdEqualTo(parentId);
    	ex.setOrderByClause("item_order desc");
    	List<Dictionary> result = this.dictionaryMapper.selectByExample(ex);
    	return result;
    }

    @Override
    public List<Dictionary> selectGender() {
        return this.selectResource("gender");
    }

    @Override
    public String selectGenderByJson() {
        return JSON.toJSONString(this.selectGender());
    }

    @Override
    public List<Dictionary> selectMembertype() {
        return this.selectResource("member_type");
    }

    @Override
    public List<Dictionary> selectMemberLevel() {
        return this.selectResource("member_Level");
    }

    @Override
    public List<Dictionary> selectRegisterFrom() {
        return this.selectResource("member_origin");
    }

	@Override
	public List<Dictionary> queryByValues(String category,List<String> values) {
		DictionaryExample ex = new DictionaryExample();
        ex.createCriteria().andValueIn(values).andCategoryEqualTo(category);
        List<Dictionary> result =dictionaryMapper.selectByExample(ex);
		return result;
	}

    /* (non-Javadoc)
     * @see cn.com.tcxy.core.GenericServiceImpl#getGenericMapper()
     */
    @Override
    protected GenericMapper<Dictionary, DictionaryExample, String> getGenericMapper() {
        return dictionaryMapper;
    }
    
    
}
