package cn.com.tcxy.cashout.prop;

import java.util.Properties;

import com.unionpay.acp.sdk.SDKConfig;

public class CashoutProperties {
	
	//银联相关的配置参数
	public static Properties unionPayCashoutProperties;
	
	//支付宝相关的配置参数
	public static Properties alipayCashoutProperties;
	
	//ChinaPay相关配置参数
	
	public static Properties chinapayCashoutProperties;
	

	public static Properties getUnionPayCashoutProperties() {
		return unionPayCashoutProperties;
	}

	public static void setUnionPayCashoutProperties(
			Properties unionPayCashoutProperties) {
		CashoutProperties.unionPayCashoutProperties = unionPayCashoutProperties;
		SDKConfig.getConfig().loadProperties(unionPayCashoutProperties);
	}

	public static Properties getAlipayCashoutProperties() {
		return alipayCashoutProperties;
	}

	public static void setAlipayCashoutProperties(Properties alipayCashoutProperties) {
		CashoutProperties.alipayCashoutProperties = alipayCashoutProperties;
	}

    public static Properties getChinapayCashoutProperties() {
        return chinapayCashoutProperties;
    }

    public static void setChinapayCashoutProperties(Properties chinapayCashoutProperties) {
        CashoutProperties.chinapayCashoutProperties = chinapayCashoutProperties;
    }
	
	
}
