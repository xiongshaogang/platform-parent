package cn.com.tcxy.cashout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import cn.com.tcxy.cashout.prop.CashoutProperties;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;

/**
 * @author axh
 *	银联取现
 */
public class UnionPayCashoutHandler{
	
	
	public static String encoding = "UTF-8";

	public static String version = "5.0.0";
	
	
	public UnionPayCashoutOrderVo createOrder(UnionPayCashoutOrderVo order){
		/**
		 * 交易请求url 从配置文件读取
		 */
		String requestBackUrl = SDKConfig.getConfig()
				.getBackRequestUrl();
		Map<String, String> resmap = submitData(prepareData(order),requestBackUrl);
		
		if("00".equals(resmap.get("respCode"))){
			//银联接受代付
			order.setStatus(UnionPayCashoutOrderVo.Status.SUCCESS.getValue());
			order.setQueryId(resmap.get("queryId"));
		}else{
			//代付失败
			order.setStatus(UnionPayCashoutOrderVo.Status.FAILURE.getValue());
			order.setErrMsg(resmap.get("respCode")+","+resmap.get("respMsg"));
		}
		return order;
	}
	
	public static Map<String, Object> prepareData(UnionPayCashoutOrderVo order) {
		Map<String, Object> contentData = new HashMap<String, Object>();
		String merId = CashoutProperties.unionPayCashoutProperties.getProperty("union.cashout.mer.id");
		String backUrl =  CashoutProperties.unionPayCashoutProperties.getProperty("union.cashout.back.url");
		
		// 系统订单号
		contentData.put("orderId", order.getOrderId());// M
		// 交易时间
		contentData.put("txnTime", order.getTxnTime());// M
		// 非绑定类交易时需上送卡号
		contentData.put("accNo", order.getAccNo());// M
		//设置用户信息　
		contentData.put("customerInfo", getCustomer(encoding,order.getCustomerNm()));//O
		//提现金额 　
		contentData.put("txnAmt", order.getTxnAmt());// M
		
		// 交易后台返回商户结果时使用，如上送，则发送商户后台交易结果通知
		contentData.put("backUrl", backUrl);// M
		// 交易商户号
		contentData.put("merId", merId);// M
		
		// 固定填写
		contentData.put("version", version);// M
		// 默认取值：UTF-8
		contentData.put("encoding", encoding);// M
		// 01RSA02 MD5 (暂不支持)
		contentData.put("signMethod", "01");// M
		// 取值：12
		contentData.put("txnType", "12");// M
		// 默认：00
		contentData.put("txnSubType", "00");// M
		contentData.put("bizType", "000401");// M
		contentData.put("channelType", "07");// M
		// 0：普通商户直连接入2：平台类商户接入
		contentData.put("accessType", "0");// M
		// 默认为156交易，填写参考公参
		contentData.put("currencyCode", "156");// M
		return contentData;
	}
	
	
	/**
	 * 持卡人信息域操作
	 * 
	 * @param encoding  编码方式
	 * @return base64后的持卡人信息域字段
	 */
	public static String getCustomer(String encoding,String customerNm) {
		StringBuffer sf = new StringBuffer("{");
		sf.append("customerNm=" + customerNm);
		sf.append("}");
		String customerInfo = sf.toString();
		try {
			return new String(SecureUtil.base64Encode(sf.toString().getBytes(
					encoding)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return customerInfo;
	}

	
	/**
	 * java main方法 数据提交　 数据组装进行提交 包含签名
	 * 
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitData(Map<String, ?> contentData,String requestUrl) {
		Map<String, String> submitFromData = (Map<String, String>) signData(contentData);
		return submitUrl(submitFromData,requestUrl);
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> signData(Map<String, ?> contentData) {
		Entry<String, String> obj = null;
		Map<String, String> submitFromData = new HashMap<String, String>();
		for (Iterator<?> it = contentData.entrySet().iterator(); it.hasNext();) {
			obj = (Entry<String, String>) it.next();
			String value = obj.getValue();
			if (StringUtils.isNotBlank(value)) {
				// 对value值进行去除前后空处理
				submitFromData.put(obj.getKey(), value.trim());
			}
		}
		/**
		 * 签名
		 */
		SDKUtil.sign(submitFromData, encoding);
		return submitFromData;
	}

	
	/**
	 * java main方法 数据提交 提交到后台
	 * 
	 * @param contentData
	 * @return 返回报文 map
	 */
	public static Map<String, String> submitUrl(
			Map<String, String> submitFromData,String requestUrl) {
		String resultString = "";
		System.out.println("submitFromData====" + submitFromData.toString());
		/**
		 * 发送
		 */
		HttpClient hc = new HttpClient(requestUrl, 300000, 30000);
		try {
			
			int status = hc.send(submitFromData, encoding);
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> resData = new HashMap<String, String>();
		/**
		 * 验证签名
		 */
		if (null != resultString && !"".equals(resultString)) {
			// 将返回结果转换为map
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, encoding)) {
				System.out.println("验证签名成功");
			} else {
				System.out.println("验证签名失败");
			}
		}
		return resData;
	}

	
}
