package cn.com.tcxy.util;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

public class RandomStringUtilsTest {

    /**
     * 输出字母与数字结合的随机数
     */
    @Test
    public void randomNumeric() {
        String str = RandomStringUtils.randomNumeric(5);
        System.out.println(str);
    }

    @Test
    public void randomAlphabetic() {
        String str = RandomStringUtils.randomAlphabetic(5);
        System.out.println(str);
    }

    @Test
    public void nextLong() {
        long str = RandomUtils.nextLong();
        System.out.println(str);
    }

}
