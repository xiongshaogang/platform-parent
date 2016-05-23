package cn.com.tcxy.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.model.Dictionary;
import cn.com.tcxy.test.BaseSpringTest;

public class DictionaryServiceTest extends BaseSpringTest {

    private static Logger LOGGER = LoggerFactory.getLogger(DictionaryServiceTest.class);

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    public void selectGenderByJson() {
        String json = this.dictionaryService.selectGenderByJson();
        LOGGER.info("json: {}", json);
    }
    
    @Test
    public void createItemOrderByParentId() {
        int itemOrder = this.dictionaryService.createItemOrderByParentId("cac41f2a76b645c68e3b0f3a29730466");
        System.out.println(itemOrder);
    }
    
    @Test
    public void selectResource(){
        String category = "advisory_status_name";
        String value = "refunded";
        String text = "";
        
        Dictionary dic = dictionaryService.selectResource(category, value, text);
    }
    
}
