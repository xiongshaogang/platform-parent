package cn.com.tcxy.util;

import org.junit.Test;

import cn.com.tcxy.util.UUIDUtil;

public class UUIDUtilTest {

    @Test
    public void getVehicleCode() {
        String vehicleCode = UUIDUtil.getVehicleCode();
        System.out.println(vehicleCode);
    }

}
