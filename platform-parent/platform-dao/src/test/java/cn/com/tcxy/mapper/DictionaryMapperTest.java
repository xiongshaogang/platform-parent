package cn.com.tcxy.mapper;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.tcxy.model.Dictionary;
import cn.com.tcxy.model.DictionaryExample;
import cn.com.tcxy.test.BaseSpringTest;

public class DictionaryMapperTest extends BaseSpringTest {
    private static final Logger logger = LoggerFactory
            .getLogger(DictionaryMapperTest.class);

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Test
    public void testSelectByExample() {
        DictionaryExample example = new DictionaryExample();
        List<Dictionary> list = this.dictionaryMapper.selectByExample(example);
        System.out.println(list.size());
    }
    


}
