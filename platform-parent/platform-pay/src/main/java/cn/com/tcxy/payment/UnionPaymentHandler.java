package cn.com.tcxy.payment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import cn.com.tcxy.payment.vo.request.UnionPayRequest;

import com.unionpay.acp.sdk.HttpClient;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKUtil;

public class UnionPaymentHandler extends PaymentHandler {

    public UnionPayRequest unionPayRequest;

	public UnionPaymentHandler(OrderVO order) {
		unionPayRequest = new UnionPayRequest();
		unionPayRequest.setOrderId(order.getOrderNo());
		unionPayRequest.setTxnAmt(String.valueOf(order.getPrice()
				.multiply(BigDecimal.valueOf(100)).intValue()));
		unionPayRequest
				.setTxnTime(org.apache.commons.lang3.time.DateFormatUtils
						.format(order.getCreateTime(), "yyyyMMddHHmmss"));
		unionPayRequest.setOrderDesc(order.getGoods());
		unionPayRequest.setCustomerIp(order.getIp());
		// 之后会不同支付渠道单独对待
		unionPayRequest.setTxnType(order.getType());
	}

    public UnionPayRequest getUnionPayRequest() {
        return unionPayRequest;
    }

    public void setUnionPayRequest(UnionPayRequest unionPayRequest) {
        this.unionPayRequest = unionPayRequest;
    }

    @Override
	public Map<String, String> sendOrderCreateRequest() {
		Map<String, String> param = unionPayRequest.getRequestParam();
		return sendHttpRequest(SDKConfig.getConfig().getAppRequestUrl(), param);
	}

	@Override
	public Map<String, String> sendOrderQueryRequest() {
		Map<String, String> param = unionPayRequest.getQueryRequestParam();
		return sendHttpRequest(SDKConfig.getConfig().getSingleQueryUrl(), param);
	}

	@Override
	public Map<String, String> sendOrderRefundRequest() {
		// 获取查询id
		Map<String, String> queryResult = this.sendOrderQueryRequest();
		if (queryResult.isEmpty()) {
			return null;
		}

		// 撤销交易
		if (queryResult.containsKey("queryId")) {
			String queryId = queryResult.get("queryId");
			Map<String, String> param = unionPayRequest
					.getRefundRequestParam(queryId);
			return sendHttpRequest(SDKConfig.getConfig().getBackRequestUrl(),
					param);
		}

		return null;
	}

	@Override
	public String getOrderCreateUrl() {
		Map<String, String> param = unionPayRequest.getRequestParam();

		StringBuffer sf = new StringBuffer();
		sf.append("<form id = \"pay_form\" action=\""
				+ SDKConfig.getConfig().getFrontRequestUrl()
				+ "\" method=\"post\">");
		if (null != param && 0 != param.size()) {
			Set<Entry<String, String>> set = param.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			while (it.hasNext()) {
				Entry<String, String> ey = it.next();
				String key = ey.getKey();
				String value = ey.getValue();
				sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\""
						+ key + "\" value=\"" + value + "\"/>");
			}
		}
		sf.append("</form>");
		return sf.toString();
	}

//	@Override
//	public PaymentResponse getOrderFromPlatform(Map<String, String> map) {
//		return new UnionPayResponse(map);
//	}

	@Override
	public String generateOrderSignatureForApp() {
		Map<String, String> map = this.sendOrderCreateRequest();
		if (map.containsKey("tn")) {
			return map.get("tn");
		}
		return null;
	}

	private Map<String, String> sendHttpRequest(String url,
			Map<String, String> param) {
		String resultString = "";
		HttpClient hc = new HttpClient(url, 30000, 30000);
		try {
			int status = hc.send(param, "utf-8");
			resultString = hc.getResult();
			if (200 == status) {
				resultString = hc.getResult();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, String> resData = new HashMap<String, String>();

		if (null != resultString && !"".equals(resultString)) {
			resData = SDKUtil.convertResultStringToMap(resultString);
			if (SDKUtil.validate(resData, "utf-8")) {
				System.out.println("=====验证签名成功====");
				return resData;
			}
		}

		System.out.println("=====验证签名失败====" + resultString);

		return null;
	}

    @Override
    public Map<String, String> getRequestParam4Wap() {
        Map<String, String> param = unionPayRequest.getRequestParam4Wap();
        return param;
    }
}
