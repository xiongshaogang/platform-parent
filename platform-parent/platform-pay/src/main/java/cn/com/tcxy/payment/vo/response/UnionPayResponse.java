package cn.com.tcxy.payment.vo.response;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;

import cn.com.tcxy.payment.prop.PaymentProperties;
import cn.com.tcxy.payment.vo.PaymentResponse;

/**
 * 用于解析、封装银联的响应报文
 *
 * @author liuluming
 */
public class UnionPayResponse extends PaymentResponse {

    private String version;
    private String certId;
    private String signature;
    private String encoding;
    private String signMethod;
    /**
     * 交易类型
     */
    private String txnType;
    /**
     * 交易子类型
     */
    private String txnSubType;
    /**
     * 产品类型
     */
    private String bizType;
    private String accessType;
    private String merId;
    /**
     * 商户订单号
     */
    private String orderId;
    /**
     * 订单发送时间
     */
    private String txnTime;
    private String currencyCode;
    private String accNo;
    private String txnAmt;
    private String billType;
    private String billNo;
    private String districtCode;
    private String additionalDistrictCode;
    private String billMonth;
    private String billDetailInfo;
    private String customerInfo;
    private String reqReserved;
    private String reserved;
    private String queryId;
    private String traceNo;
    private String traceTime;
    private String settleDate;
    private String settleCurrencyCode;
    private String settleAmt;
    private String exchangeRate;
    private String exchangeDate;
    /**
     * 交易结果码
     */
    private String respCode;
    private String respMsg;
    /**
     * 原始交易结果码
     */
    private String origRespCode;
    // 00 ：查询交易
    // 01 ：消费
    // 02 ：预授权
    // 03 ：预授权完成
    // 04 ：退货
    // 05 ：圈存
    // 11 ：代收
    // 12 ：代付
    // 13 ：账单支付
    // 14 ：转账（保留）
    // 21 ：批量交易
    // 22 ：批量查询
    // 31 ：消费撤销
    // 32 ：预授权撤销
    // 33 ：预授权完成撤销
    // 71 ：余额查询
    // 72 ：实名认证 -建立绑定关系
    // 73 ：账单查询
    // 74 ：解除绑定关系
    // 75 ：查询绑定关系
    // 77 ：发送短信验证码交易
    // 78 ：开通查询交易
    // 79 ：开通交易
    // 94 ：IC卡脚本通知
    /**
     * 交易类型：查询交易
     */
    public static final String TXNTYPE_00 = "00";
    /**
     * 交易类型：消费(支付)
     */
    public static final String TXNTYPE_01 = "01";
    /**
     * 交易类型：预授权
     */
    public static final String TXNTYPE_02 = "02";
    /**
     * 交易类型：预授权完成
     */
    public static final String TXNTYPE_03 = "03";
    /**
     * 交易类型：退货
     */
    public static final String TXNTYPE_04 = "04";
    /**
     * 交易类型：圈存
     */
    public static final String TXNTYPE_05 = "05";
    /**
     * 交易结果:交易成功
     */
    public static final String RESPCODE_00 = "00";
    /**
     * 交易结果:
     */
    public static final String RESPCODE_01 = "01";
    /**
     * 交易结果:
     */
    public static final String RESPCODE_02 = "02";
    /**
     * 交易结果:
     */
    public static final String RESPCODE_03 = "03";
    /**
     * 交易结果:
     */
    public static final String RESPCODE_04 = "04";
    /**
     * 交易结果:
     */
    public static final String RESPCODE_05 = "05";
    
    /**
     * 原始交易结果:交易成功
     */
    public static final String ORIGRESPCODE_00 = "00";
    /**
     * 原始交易结果:
     */
    public static final String ORIGRESPCODE_01 = "01";
    /**
     * 原始交易结果:
     */
    public static final String ORIGRESPCODE_02 = "02";
    /**
     * 原始交易结果:
     */
    public static final String ORIGRESPCODE_03 = "03";
    /**
     * 原始交易结果:
     */
    public static final String ORIGRESPCODE_04 = "04";
    /**
     * 原始交易结果:
     */
    public static final String ORIGRESPCODE_05 = "05";

    private Map<String, String> map;

    // 初始化 将map中数据转入对应参数
    public UnionPayResponse(Map<String, String> map) {
        SDKConfig.getConfig().loadProperties(PaymentProperties.unionPayProperties);

        this.map = map;

        if (map.containsKey("version")) {
            this.version = map.get("version");
        }
        if (map.containsKey("certId")) {
            this.certId = map.get("certId");
        }
        if (map.containsKey("signature")) {
            this.signature = map.get("signature");
        }
        if (map.containsKey("encoding")) {
            this.encoding = map.get("encoding");
        }
        if (map.containsKey("signMethod")) {
            this.signMethod = map.get("signMethod");
        }
        if (map.containsKey("txnType")) {
            this.txnType = map.get("txnType");
        }
        if (map.containsKey("txnSubType")) {
            this.txnSubType = map.get("txnSubType");
        }
        if (map.containsKey("bizType")) {
            this.bizType = map.get("bizType");
        }
        if (map.containsKey("accessType")) {
            this.accessType = map.get("accessType");
        }
        if (map.containsKey("merId")) {
            this.merId = map.get("merId");
        }
        if (map.containsKey("orderId")) {
            this.orderId = map.get("orderId");
        }
        if (map.containsKey("txnTime")) {
            this.txnTime = map.get("txnTime");
        }
        if (map.containsKey("currencyCode")) {
            this.currencyCode = map.get("currencyCode");
        }
        if (map.containsKey("accNo")) {
            this.accNo = map.get("accNo");
        }
        if (map.containsKey("txnAmt")) {
            this.txnAmt = map.get("txnAmt");
        }
        if (map.containsKey("billType")) {
            this.billType = map.get("billType");
        }
        if (map.containsKey("billNo")) {
            this.billNo = map.get("billNo");
        }
        if (map.containsKey("districtCode")) {
            this.districtCode = map.get("districtCode");
        }
        if (map.containsKey("additionalDistrictCode")) {
            this.additionalDistrictCode = map.get("additionalDistrictCode");
        }
        if (map.containsKey("billMonth")) {
            this.billMonth = map.get("billMonth");
        }
        if (map.containsKey("billDetailInfo")) {
            this.billDetailInfo = map.get("billDetailInfo");
        }
        if (map.containsKey("customerInfo")) {
            this.customerInfo = map.get("customerInfo");
        }
        if (map.containsKey("reqReserved")) {
            this.reqReserved = map.get("reqReserved");
        }
        if (map.containsKey("reserved")) {
            this.reserved = map.get("reserved");
        }
        if (map.containsKey("queryId")) {
            this.queryId = map.get("queryId");
        }
        if (map.containsKey("traceNo")) {
            this.traceNo = map.get("traceNo");
        }
        if (map.containsKey("traceTime")) {
            this.traceTime = map.get("traceTime");
        }
        if (map.containsKey("settleDate")) {
            this.settleDate = map.get("settleDate");
        }
        if (map.containsKey("settleCurrencyCode")) {
            this.settleCurrencyCode = map.get("settleCurrencyCode");
        }
        if (map.containsKey("settleAmt")) {
            this.settleAmt = map.get("settleAmt");
        }
        if (map.containsKey("exchangeRate")) {
            this.exchangeRate = map.get("exchangeRate");
        }
        if (map.containsKey("exchangeDate")) {
            this.exchangeDate = map.get("exchangeDate");
        }
        if (map.containsKey("respCode")) {
            this.respCode = map.get("respCode");
        }
        if (map.containsKey("respMsg")) {
            this.respMsg = map.get("respMsg");
        }
        if (map.containsKey("origRespCode")) {
            this.origRespCode = map.get("origRespCode");
        }

    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
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

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
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

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getAdditionalDistrictCode() {
        return additionalDistrictCode;
    }

    public void setAdditionalDistrictCode(String additionalDistrictCode) {
        this.additionalDistrictCode = additionalDistrictCode;
    }

    public String getBillMonth() {
        return billMonth;
    }

    public void setBillMonth(String billMonth) {
        this.billMonth = billMonth;
    }

    public String getBillDetailInfo() {
        return billDetailInfo;
    }

    public void setBillDetailInfo(String billDetailInfo) {
        this.billDetailInfo = billDetailInfo;
    }

    public String getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(String customerInfo) {
        this.customerInfo = customerInfo;
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

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }

    public String getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(String traceTime) {
        this.traceTime = traceTime;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getSettleCurrencyCode() {
        return settleCurrencyCode;
    }

    public void setSettleCurrencyCode(String settleCurrencyCode) {
        this.settleCurrencyCode = settleCurrencyCode;
    }

    public String getSettleAmt() {
        return settleAmt;
    }

    public void setSettleAmt(String settleAmt) {
        this.settleAmt = settleAmt;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getExchangeDate() {
        return exchangeDate;
    }

    public void setExchangeDate(String exchangeDate) {
        this.exchangeDate = exchangeDate;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    @Override
    public String getOrderStatus() {

        if (StringUtils.isEmpty(respCode)) {
            return ORDERSTATUS_FAIL;
        }

        if ("00".equals(respCode)) {
            return ORDERSTATUS_SUCCESS;
        }

        return ORDERSTATUS_FAIL;
    }

    public String getOrigRespCode() {
        return origRespCode;
    }

    public void setOrigRespCode(String origRespCode) {
        this.origRespCode = origRespCode;
    }

    @Override
    public boolean checkSignature() {
        return SDKUtil.validate(map, encoding);
    }
}
