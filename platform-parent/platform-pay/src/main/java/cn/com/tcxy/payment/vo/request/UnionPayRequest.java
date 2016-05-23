package cn.com.tcxy.payment.vo.request;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.com.tcxy.payment.prop.PaymentProperties;
import cn.com.tcxy.payment.vo.PaymentRequest;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;
import com.unionpay.acp.sdk.SecureUtil;


/**
 * 用于封装银联的请求参数。
 *
 * @author liuluming
 */
public class UnionPayRequest extends PaymentRequest{
	
	
//	00 查询交易
	public static final String TXN_TYPE_PAYMENT_QUERY ="00";
//	01：消费
	public static final String TXN_TYPE_PAYMENT_PAY ="01";
//	02：预授权
	public static final String TXN_TYPE_PAYMENT_YU ="02";
//	03：预授权完成
	public static final String TXN_TYPE_PAYMENT_YU_COMPLISH ="03";
//	04：退货
	public static final String TXN_TYPE_PAYMENT_REFUND ="04";
//	05: 圈存
//	11：代收
//	12：代付
//	13：账单支付
//	14：转账（保留）
//	21：批量交易
//	22：批量查询
//	31：消费撤销
	public static String TXN_TYPE_PAYMENT_CANCEL ="31";
//	32：预授权撤销
	public static String TXN_TYPE_PAYMENT_YU_CANCEL ="32";
//	33：预授权完成撤销
	public static String TXN_TYPE_PAYMENT_YU_COMPLISH_CANCEL ="33";
//	71：余额查询
//	72：实名认证-建立绑定关系
//	73： 账单查询
//	74：解除绑定关系
//	75：查询绑定关系
//	77：发送短信验证码交易
//	78：开通查询交易
//	79：开通交易
//	94：IC卡脚本通知
	
	//需要tcxy配置
	private String merId;//商户代码
	
	private String frontUrl;
	
	private String backUrl;
	
	//必填参数
    public static String version = "5.0.0";// 版本号
	private String signature;//签名-- 签名时候放入
	private String certId;//证书ID-- 签名时候放入
	private String signMethod="01";//签名方法
	private String txnType="01";//交易类型
	private String txnSubType="01";//交易子类
	private String bizType="000201";//产品类型
	private String channelType="08";//渠道类型
	private String orderId;//商户订单号
	private String txnTime;//订单发送时间 20150209103939
	private String currencyCode="156";//交易币种 156
	
	//选填参数
    public static String encoding = "UTF-8";
	private String accessType = "0"; //// 接入类型:商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
	private String subMerId;//二级商户代码
	private String subMerName;//二级商户全称 
	private String subMerAbbr;//二级商户简称 
	private String accType="01";//帐号类型 
	private String accNo;//帐号
	private String txnAmt;//交易金额
	private String customerInfo;//银行卡验证信息及身份信息 
	private String orderTimeout;//订单接收超时时间（防钓鱼使用）
	private String payTimeout;//订单支付超时时间 
	private String termId;//终端号
	private String reqReserved;//请求方保留域 
	private String reserved;
	private String riskRateInfo;
	private String encryptCertId;
	private String frontFailUrl;
	private String instalTransInfo;
	private String defaultPayType;
	private String issInsCode;
	private String supPayType;
	private String userMac;
	private String customerIp;
	private String cardTransData;
	private String vpcTransData;
	private String orderDesc;
	
	//初始化加载
	public UnionPayRequest(){
		SDKConfig.getConfig().loadProperties(PaymentProperties.unionPayProperties);
		merId = PaymentProperties.unionPayProperties.getProperty("union.mer.id");
		frontUrl =  PaymentProperties.unionPayProperties.getProperty("union.front.url");
		backUrl =  PaymentProperties.unionPayProperties.getProperty("union.back.url");
	}
	
    @Override
    public Map<String, String> getRequestParam4Wap() {
        Map<String, String> map = new HashMap<String, String>();

        if (StringUtils.isNotEmpty(merId)) {
            map.put("merId", merId);
        }
        if (StringUtils.isNotEmpty(frontUrl)) {
            map.put("frontUrl", frontUrl);
        }
        if (StringUtils.isNotEmpty(backUrl)) {
            map.put("backUrl", backUrl);
        }
        if (StringUtils.isNotEmpty(version)) {
            map.put("version", version);
        }
        if (StringUtils.isNotEmpty(signMethod)) {
            map.put("signMethod", signMethod);
        }

        if (StringUtils.isNotEmpty(txnType)) {
            map.put("txnType", txnType);
        } else {
            // 默认消费
            map.put("txnType", "01");
        }

        if (StringUtils.isNotEmpty(txnSubType)) {
            map.put("txnSubType", txnSubType);
        }
        if (StringUtils.isNotEmpty(bizType)) {
            map.put("bizType", bizType);
        }
        if (StringUtils.isNotEmpty(channelType)) {
            map.put("channelType", channelType);
        }
        if (StringUtils.isNotEmpty(orderId)) {
            map.put("orderId", orderId);
        }
        if (StringUtils.isNotEmpty(txnTime)) {
            map.put("txnTime", txnTime);
        }
        if (StringUtils.isNotEmpty(currencyCode)) {
            map.put("currencyCode", currencyCode);
        }
        if (StringUtils.isNotEmpty(encoding)) {
            map.put("encoding", encoding);
        }
        if (StringUtils.isNotEmpty(accessType)) {
            map.put("accessType", accessType);
        }
        if (StringUtils.isNotEmpty(subMerId)) {
            map.put("subMerId", subMerId);
        }
        if (StringUtils.isNotEmpty(subMerName)) {
            map.put("subMerName", subMerName);
        }
        if (StringUtils.isNotEmpty(subMerAbbr)) {
            map.put("subMerAbbr", subMerAbbr);
        }
        if (StringUtils.isNotEmpty(accType)) {
            map.put("accType", accType);
        }
        if (StringUtils.isNotEmpty(accNo)) {
            map.put("accNo", accNo);
        }
        if (StringUtils.isNotEmpty(txnAmt)) {
            map.put("txnAmt", txnAmt);
        }
        if (StringUtils.isNotEmpty(customerInfo)) {
            map.put("customerInfo", customerInfo);
        }
        if (StringUtils.isNotEmpty(orderTimeout)) {
            map.put("orderTimeout", orderTimeout);
        }
        if (StringUtils.isNotEmpty(payTimeout)) {
            map.put("payTimeout", payTimeout);
        }
        if (StringUtils.isNotEmpty(termId)) {
            map.put("termId", termId);
        }
        if (StringUtils.isNotEmpty(reqReserved)) {
            map.put("reqReserved", reqReserved);
        }
        if (StringUtils.isNotEmpty(reserved)) {
            map.put("reserved", reserved);
        }
        if (StringUtils.isNotEmpty(riskRateInfo)) {
            map.put("riskRateInfo", riskRateInfo);
        }
        if (StringUtils.isNotEmpty(encryptCertId)) {
            map.put("encryptCertId", encryptCertId);
        }
        if (StringUtils.isNotEmpty(frontFailUrl)) {
            map.put("frontFailUrl", frontFailUrl);
        }
        if (StringUtils.isNotEmpty(instalTransInfo)) {
            map.put("instalTransInfo", instalTransInfo);
        }
        if (StringUtils.isNotEmpty(defaultPayType)) {
            map.put("defaultPayType", defaultPayType);
        }
        if (StringUtils.isNotEmpty(issInsCode)) {
            map.put("issInsCode", issInsCode);
        }
        if (StringUtils.isNotEmpty(supPayType)) {
            map.put("supPayType", supPayType);
        }
        if (StringUtils.isNotEmpty(userMac)) {
            map.put("userMac", userMac);
        }
        if (StringUtils.isNotEmpty(customerIp)) {
            map.put("customerIp", customerIp);
        }
        if (StringUtils.isNotEmpty(cardTransData)) {
            map.put("cardTransData", cardTransData);
        }
        if (StringUtils.isNotEmpty(vpcTransData)) {
            map.put("vpcTransData", vpcTransData);
        }
        if (StringUtils.isNotEmpty(orderDesc)) {
            map.put("orderDesc", orderDesc);
        }

        return map;
    }

	/**
	 * 默认参数
	 */
	@Override
	public Map<String, String> getRequestParam() {
		Map<String, String> map =  new HashMap<String, String>();
		
		if(StringUtils.isNotEmpty(merId)){map.put("merId",merId);}
		if(StringUtils.isNotEmpty(frontUrl)){map.put("frontUrl",frontUrl);}
		if(StringUtils.isNotEmpty(backUrl)){map.put("backUrl",backUrl);}
		if(StringUtils.isNotEmpty(version)){map.put("version",version);}
		if(StringUtils.isNotEmpty(signMethod)){map.put("signMethod",signMethod);}
		
		if(StringUtils.isNotEmpty(txnType)){
			map.put("txnType",txnType);
		}else{
			//默认消费
			map.put("txnType","01");
		}
		
		if(StringUtils.isNotEmpty(txnSubType)){map.put("txnSubType",txnSubType);}
		if(StringUtils.isNotEmpty(bizType)){map.put("bizType",bizType);}
		if(StringUtils.isNotEmpty(channelType)){map.put("channelType",channelType);}
		if(StringUtils.isNotEmpty(orderId)){map.put("orderId",orderId);}
		if(StringUtils.isNotEmpty(txnTime)){map.put("txnTime",txnTime);}
		if(StringUtils.isNotEmpty(currencyCode)){map.put("currencyCode",currencyCode);}
		if(StringUtils.isNotEmpty(encoding)){map.put("encoding",encoding);}
		if(StringUtils.isNotEmpty(accessType)){map.put("accessType",accessType);}
		if(StringUtils.isNotEmpty(subMerId)){map.put("subMerId",subMerId);}
		if(StringUtils.isNotEmpty(subMerName)){map.put("subMerName",subMerName);}
		if(StringUtils.isNotEmpty(subMerAbbr)){map.put("subMerAbbr",subMerAbbr);}
		if(StringUtils.isNotEmpty(accType)){map.put("accType",accType);}
		if(StringUtils.isNotEmpty(accNo)){map.put("accNo",accNo);}
		if(StringUtils.isNotEmpty(txnAmt)){map.put("txnAmt",txnAmt);}
		if(StringUtils.isNotEmpty(customerInfo)){map.put("customerInfo",customerInfo);}
		if(StringUtils.isNotEmpty(orderTimeout)){map.put("orderTimeout",orderTimeout);}
		if(StringUtils.isNotEmpty(payTimeout)){map.put("payTimeout",payTimeout);}
		if(StringUtils.isNotEmpty(termId)){map.put("termId",termId);}
		if(StringUtils.isNotEmpty(reqReserved)){map.put("reqReserved",reqReserved);}
		if(StringUtils.isNotEmpty(reserved)){map.put("reserved",reserved);}
		if(StringUtils.isNotEmpty(riskRateInfo)){map.put("riskRateInfo",riskRateInfo);}
		if(StringUtils.isNotEmpty(encryptCertId)){map.put("encryptCertId",encryptCertId);}
		if(StringUtils.isNotEmpty(frontFailUrl)){map.put("frontFailUrl",frontFailUrl);}
		if(StringUtils.isNotEmpty(instalTransInfo)){map.put("instalTransInfo",instalTransInfo);}
		if(StringUtils.isNotEmpty(defaultPayType)){map.put("defaultPayType",defaultPayType);}
		if(StringUtils.isNotEmpty(issInsCode)){map.put("issInsCode",issInsCode);}
		if(StringUtils.isNotEmpty(supPayType)){map.put("supPayType",supPayType);}
		if(StringUtils.isNotEmpty(userMac)){map.put("userMac",userMac);}
		if(StringUtils.isNotEmpty(customerIp)){map.put("customerIp",customerIp);}
		if(StringUtils.isNotEmpty(cardTransData)){map.put("cardTransData",cardTransData);}
		if(StringUtils.isNotEmpty(vpcTransData)){map.put("vpcTransData",vpcTransData);}
		if(StringUtils.isNotEmpty(orderDesc)){map.put("orderDesc",orderDesc);}

		sign(map);
		
		return map;
	}
	
	/**
	 * 查询参数
	 * 
	 */
	public Map<String, String> getQueryRequestParam() {
		Map<String, String> map =  new HashMap<String, String>();
        // 以下参数为必填。
		if(StringUtils.isNotEmpty(version)){map.put("version",version);}
		if(StringUtils.isNotEmpty(encoding)){map.put("encoding",encoding);}
		if(StringUtils.isNotEmpty(signMethod)){map.put("signMethod",signMethod);}
		map.put("txnType",TXN_TYPE_PAYMENT_QUERY);
		map.put("txnSubType","00");
		map.put("bizType","000000");
        map.put("channelType", "08");
		if(StringUtils.isNotEmpty(accessType)){map.put("accessType",accessType);}
		if(StringUtils.isNotEmpty(merId)){map.put("merId",merId);}
        // 以下两个参数需要单独put到map中。
//		if(StringUtils.isNotEmpty(txnTime)){map.put("txnTime",txnTime);}
//		if(StringUtils.isNotEmpty(orderId)){map.put("orderId",orderId);}
		
        // sign(map);
		
		return map;
		
	}
	
	/**
	 * 撤销、退款参数
	 * 
	 */
	public Map<String, String> getRefundRequestParam(String origQryId) {
		
		Map<String, String> map =  new HashMap<String, String>();
		if(StringUtils.isNotEmpty(origQryId)){map.put("origQryId",origQryId);}
		
		//此处是回调的地址---撤销交易
		if(StringUtils.isNotEmpty(backUrl)){map.put("backUrl",backUrl);}
		
		//退款
		map.put("txnType",TXN_TYPE_PAYMENT_REFUND);
		map.put("channelType","07");
		map.put("txnSubType","00");
		if(StringUtils.isNotEmpty(version)){map.put("version",version);}
		if(StringUtils.isNotEmpty(encoding)){map.put("encoding",encoding);}
		if(StringUtils.isNotEmpty(signMethod)){map.put("signMethod",signMethod);}
		if(StringUtils.isNotEmpty(bizType)){map.put("bizType",bizType);}
		if(StringUtils.isNotEmpty(accessType)){map.put("accessType",accessType);}
		if(StringUtils.isNotEmpty(merId)){map.put("merId",merId);}
		if(StringUtils.isNotEmpty(txnTime)){map.put("txnTime",txnTime);}
	    if(StringUtils.isNotEmpty(currencyCode)){map.put("currencyCode",currencyCode);}
//		if(StringUtils.isNotEmpty(txnAmt)){map.put("txnAmt",txnAmt);}
//		if(StringUtils.isNotEmpty(orderId)){map.put("orderId",orderId+"2");}
		
//	sign(map);
		
		return map;
		
	}

    /**
     * 构造HTTP POST交易表单的方法示例
     * 
     * @param action
     *            表单提交地址
     * @param hiddens
     *            以MAP形式存储的表单键值
     * @return 构造好的HTTP POST交易表单
     */
    public String createHtml(String action, Map<String, String> hiddens) {
        StringBuffer sf = new StringBuffer();
        sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body>");
        sf.append("<form id = \"pay_form\" action=\"" + action + "\" method=\"post\">");
        if (null != hiddens && 0 != hiddens.size()) {
            Set<Entry<String, String>> set = hiddens.entrySet();
            Iterator<Entry<String, String>> it = set.iterator();
            while (it.hasNext()) {
                Entry<String, String> ey = it.next();
                String key = ey.getKey();
                String value = ey.getValue();
                sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + value + "\"/>");
            }
        }
        sf.append("</form>");
        sf.append("</body>");
        sf.append("<script type=\"text/javascript\">");
        sf.append("document.all.pay_form.submit();");
        sf.append("</script>");
        sf.append("</html>");
        return sf.toString();
    }

    /**
     * java main方法 数据提交 　　 对数据进行签名
     * 
     * @param contentData
     * @return　签名后的map对象
     */
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
                System.out.println(obj.getKey() + "-->" + String.valueOf(value));
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
    public static Map<String, String> submitUrl(Map<String, String> submitFromData, String requestUrl) {
        String resultString = "";
        System.out.println("requestUrl====" + requestUrl);
        System.out.println("submitFromData====" + submitFromData.toString());
        /**
         * 发送
         */
        HttpClient hc = new HttpClient(requestUrl, 30000, 30000);
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
            // 打印返回报文
            System.out.println("打印返回报文：" + resultString);
        }
        return resData;
    }

    /**
     * 解析返回文件
     */
    public static void deCodeFileContent(Map<String, String> resData) {
        // 解析返回文件
        String fileContent = resData.get(SDKConstants.param_fileContent);
        if (null != fileContent && !"".equals(fileContent)) {
            try {
                byte[] fileArray = SecureUtil.inflater(SecureUtil.base64Decode(fileContent.getBytes(encoding)));
                String root = "D:\\";
                String filePath = null;
                if (SDKUtil.isEmpty(resData.get("fileName"))) {
                    filePath = root + File.separator + resData.get("merId") + "_" + resData.get("batchNo") + "_"
                            + resData.get("txnTime") + ".txt";
                } else {
                    filePath = root + File.separator + resData.get("fileName");
                }
                File file = new File(filePath);
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                out.write(fileArray, 0, fileArray.length);
                out.flush();
                out.close();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java main方法 数据提交　 数据组装进行提交 包含签名
     * 
     * @param contentData
     * @return 返回报文 map
     */
    public static Map<String, String> submitDate(Map<String, ?> contentData, String requestUrl) {

        Map<String, String> submitFromData = signData(contentData);

        return submitUrl(submitFromData, requestUrl);
    }

    /**
     * 持卡人信息域操作
     * 
     * @param encoding
     *            编码方式
     * @return base64后的持卡人信息域字段
     */
    public static String getCustomer(String encoding) {
        StringBuffer sf = new StringBuffer("{");
        // 证件类型
        String certifTp = "01";
        // 证件号码
        String certifId = "1301212386859081945";
        // 姓名
        String customerNm = "测试";
        // 手机号
        String phoneNo = "18613958987";
        // 短信验证码
        String smsCode = "123311";
        // 持卡人密码
        String pin = "123213";
        // cvn2
        String cvn2 = "400";
        // 有效期
        String expired = "1212";
        sf.append("certifTp=" + certifTp + SDKConstants.AMPERSAND);
        sf.append("certifId=" + certifId + SDKConstants.AMPERSAND);
        sf.append("customerNm=" + customerNm + SDKConstants.AMPERSAND);
        sf.append("phoneNo=" + phoneNo + SDKConstants.AMPERSAND);
        sf.append("smsCode=" + smsCode + SDKConstants.AMPERSAND);
        // 密码加密
        sf.append("pin=" + SDKUtil.encryptPin("622188123456789", pin, encoding) + SDKConstants.AMPERSAND);
        // 密码不加密
        // sf.append("pin="+pin + SDKConstants.AMPERSAND);
        // cvn2加密
        // sf.append(SDKUtil.encrptCvn2(cvn2, encoding) +
        // SDKConstants.AMPERSAND);
        // cvn2不加密
        sf.append("cvn2=" + cvn2 + SDKConstants.AMPERSAND);
        // 有效期加密
        // sf.append(SDKUtil.encrptAvailable(expired, encoding));
        // 有效期不加密
        sf.append("expired=" + expired);
        sf.append("}");
        String customerInfo = sf.toString();
        try {
            return new String(SecureUtil.base64Encode(sf.toString().getBytes(encoding)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerInfo;
    }


	@Override
    public void sign(Map<String, String> map) {
		SDKUtil.sign(map, encoding);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public void setSignMethod(String signMethod) {
		this.signMethod = signMethod;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getSubMerId() {
		return subMerId;
	}

	public void setSubMerId(String subMerId) {
		this.subMerId = subMerId;
	}

	public String getSubMerName() {
		return subMerName;
	}

	public void setSubMerName(String subMerName) {
		this.subMerName = subMerName;
	}

	public String getSubMerAbbr() {
		return subMerAbbr;
	}

	public void setSubMerAbbr(String subMerAbbr) {
		this.subMerAbbr = subMerAbbr;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getCustomerInfo() {
		return customerInfo;
	}

	public void setCustomerInfo(String customerInfo) {
		this.customerInfo = customerInfo;
	}

	public String getOrderTimeout() {
		return orderTimeout;
	}

	public void setOrderTimeout(String orderTimeout) {
		this.orderTimeout = orderTimeout;
	}

	public String getPayTimeout() {
		return payTimeout;
	}

	public void setPayTimeout(String payTimeout) {
		this.payTimeout = payTimeout;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getReqReserved() {
		return reqReserved;
	}

	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

	public String getRiskRateInfo() {
		return riskRateInfo;
	}

	public void setRiskRateInfo(String riskRateInfo) {
		this.riskRateInfo = riskRateInfo;
	}

	public String getEncryptCertId() {
		return encryptCertId;
	}

	public void setEncryptCertId(String encryptCertId) {
		this.encryptCertId = encryptCertId;
	}

	public String getFrontFailUrl() {
		return frontFailUrl;
	}

	public void setFrontFailUrl(String frontFailUrl) {
		this.frontFailUrl = frontFailUrl;
	}

	public String getInstalTransInfo() {
		return instalTransInfo;
	}

	public void setInstalTransInfo(String instalTransInfo) {
		this.instalTransInfo = instalTransInfo;
	}

	public String getDefaultPayType() {
		return defaultPayType;
	}

	public void setDefaultPayType(String defaultPayType) {
		this.defaultPayType = defaultPayType;
	}

	public String getIssInsCode() {
		return issInsCode;
	}

	public void setIssInsCode(String issInsCode) {
		this.issInsCode = issInsCode;
	}

	public String getSupPayType() {
		return supPayType;
	}

	public void setSupPayType(String supPayType) {
		this.supPayType = supPayType;
	}

	public String getUserMac() {
		return userMac;
	}

	public void setUserMac(String userMac) {
		this.userMac = userMac;
	}

	public String getCustomerIp() {
		return customerIp;
	}

	public void setCustomerIp(String customerIp) {
		this.customerIp = customerIp;
	}

	public String getCardTransData() {
		return cardTransData;
	}

	public void setCardTransData(String cardTransData) {
		this.cardTransData = cardTransData;
	}

	public String getVpcTransData() {
		return vpcTransData;
	}

	public void setVpcTransData(String vpcTransData) {
		this.vpcTransData = vpcTransData;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getFrontUrl() {
		return frontUrl;
	}

	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}
}
