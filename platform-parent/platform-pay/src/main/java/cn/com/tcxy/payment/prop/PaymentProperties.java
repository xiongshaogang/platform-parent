package cn.com.tcxy.payment.prop;

import java.util.Properties;

public class PaymentProperties {
	
	//银联相关的配置参数
	public static Properties unionPayProperties;
	
	//银联相关的配置参数
	public static Properties wechatProperties;
	
	//支付宝相关的配置参数
	public static Properties alipayProperties;

	public Properties getUnionPayProperties() {
		return unionPayProperties;
	}
	
	public void setUnionPayProperties(Properties unionPayProperties) {
		PaymentProperties.unionPayProperties = unionPayProperties;
	}

	public Properties getWechatProperties() {
		return wechatProperties;
	}

	public void setWechatProperties(Properties wechatProperties) {
		PaymentProperties.wechatProperties = wechatProperties;
	}

	public Properties getAlipayProperties() {
		return alipayProperties;
	}

	public void setAlipayProperties(Properties alipayProperties) {
		PaymentProperties.alipayProperties = alipayProperties;
	}
	
	//加载union系统配置
//	static {
//		String classPath = UnionPayRequest.class.getResource("/").getPath();
//		SDKConfig.getConfig().loadPropertiesFromPath(classPath+"config/");
//		
//		//SDKConfig.getConfig().loadProperties(pro);
//		
//		if(UnionPayRequest.merId== null){
//			Properties prop = new Properties();
//			try {
//				prop.load(UnionPayRequest.class.getResource("/payment.properties").openStream());
//				UnionPayRequest.merId = prop.getProperty("union.mer.id");
//				UnionPayRequest.frontUrl =  prop.getProperty("union.front.url");
//				UnionPayRequest.backUrl =  prop.getProperty("union.back.url");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
}
