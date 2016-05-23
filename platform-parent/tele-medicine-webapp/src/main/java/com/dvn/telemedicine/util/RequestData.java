package com.dvn.telemedicine.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dvn.telemedicine.entity.x.Xmember;

public class RequestData {
	protected static Logger logger = LoggerFactory.getLogger(RequestData.class);
	private static ObjectMapper maper = new ObjectMapper();
	private static String mip=ReadPropertiesUtil.getProperties("uc_ip", "application.properties");
	/**
	 * 请求客户端API
	 */
	public static Xmember getPayUserId(String memberId) {
		logger.info("----------------------------获取成员详情 start-------------------------------");
		String SET_EXPLORER = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.19) Gecko/20081202 Iceweasel/2.0.0.19 (Debian-2.0.0.19-0etch1)";
		PostMethod Post = null;
		Xmember m=null;
		try {
			HttpClient client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.USER_AGENT, SET_EXPLORER);
			client.getParams().setContentCharset("utf-8");
			client.getParams().setHttpElementCharset("utf-8");
			String url = mip+"/api/uc/getMemberInfo";// 推荐
//			String url = "http://220.248.121.43:8877/api/uc/getMemberInfo";
			Post = new PostMethod(url);
			Post.setRequestBody(new NameValuePair[] { 
			new NameValuePair("memberId", memberId)});
			String resultStr = null;
			int result = 0;
			result = client.executeMethod(Post);
			if (result == HttpStatus.SC_OK) {
				resultStr = Post.getResponseBodyAsString();// 成功响应请求 获取响应结果
				JsonNode  json = maper.readTree(resultStr);
				JsonNode  jsons=json.get("result");
				m=new Xmember();
				if(jsons.toString()!=null && !("").equals(jsons.toString()) && !("null").equals(jsons.toString())){
					m.setId(memberId);
					m.setName(jsons.get("name").getTextValue());
					m.setMainUserId(jsons.get("mainUserId").getLongValue());
					m.setIdCard(jsons.get("idCard").getTextValue());
					m.setPhone(jsons.get("phone").getTextValue());
					m.setAddress(jsons.get("address").getTextValue());
					m.setUrl(jsons.get("url").getTextValue());
					String updateTime=String.valueOf(jsons.get("updateDate"));
					if(updateTime!=null && !("").equals(updateTime) && !("null").equals(updateTime)){
						m.setUpdateDate(new Date(Long.valueOf(updateTime)));
					}
					String height =String.valueOf(jsons.get("height"));
					String weight =String.valueOf(jsons.get("weight"));
					String age =String.valueOf(jsons.get("age"));
					String sex =String.valueOf(jsons.get("sex"));
					String isTemporary=String.valueOf(jsons.get("isTemporary"));
					if(height!=null && !("").equals(height)){
						m.setHeight(Long.valueOf(height));
					}
					if(weight!=null && !("").equals(weight)){
						m.setWeight(Long.valueOf(weight));
					}
					if(age!=null && !("").equals(age)){
						m.setAge(Long.valueOf(age));
					}
					if(sex!=null && !("").equals(sex)){
						m.setSex(Long.valueOf(sex));
					}
					m.setIsTemporary(Integer.parseInt(isTemporary));
					logger.info("----------------------------获取成员详情   请求成功-------------------------------");
				}
			}
		} catch (Exception e) {
			logger.info("----------------------------获取成员详情   请求错误-------------------------------");
		} 
		logger.info("----------------------------获取成员详情   请求结束-------------------------------");
		Post.releaseConnection();
		return m;
	}
	
	/**
	 * 请求客户端API
	 */
	public static void updateLoginDate(String memberId) {
		logger.info("----------------------------修改成员登录时间 start-------------------------------");
		String SET_EXPLORER = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.19) Gecko/20081202 Iceweasel/2.0.0.19 (Debian-2.0.0.19-0etch1)";
		PostMethod Post = null;
		try {
			HttpClient client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.USER_AGENT, SET_EXPLORER);
			client.getParams().setContentCharset("utf-8");
			client.getParams().setHttpElementCharset("utf-8");
			String url = mip+"/api/uc/unloginWarn";// 推荐
//			String url = "http://220.248.121.43:8877/api/uc/getMemberInfo";
			Post = new PostMethod(url);
			Post.setRequestBody(new NameValuePair[] { 
			new NameValuePair("memberId", memberId)});
			String resultStr = null;
			int result = 0;
			result = client.executeMethod(Post);
			if (result == HttpStatus.SC_OK) {
				resultStr = Post.getResponseBodyAsString();// 成功响应请求 获取响应结果
				System.out.println(resultStr);
			}
		} catch (Exception e) {
			logger.info("----------------------------修改成员登录时间   请求错误-------------------------------");
		} 
		logger.info("----------------------------修改成员登录时间   请求结束-------------------------------");
		Post.releaseConnection();
	}
	
	public static List<Xmember> getUserCardId() {
		logger.info("----------------------------获取所有成员用户详情 start-------------------------------");
		String SET_EXPLORER = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.19) Gecko/20081202 Iceweasel/2.0.0.19 (Debian-2.0.0.19-0etch1)";
		PostMethod Post = null;
		List<Xmember> m= new ArrayList<Xmember>();
		try {
			HttpClient client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.USER_AGENT, SET_EXPLORER);
			client.getParams().setContentCharset("utf-8");
			client.getParams().setHttpElementCharset("utf-8");
			String url = mip+"/api/uc/getListUserAndMember";// 推荐
//			String url = "http://localhost:8877/api/uc/getListUserAndMember";
			Post = new PostMethod(url);
			String resultStr = null;
			int result = 0;
			result = client.executeMethod(Post);
			if (result == HttpStatus.SC_OK) {
				resultStr = Post.getResponseBodyAsString();// 成功响应请求 获取响应结果
				JsonNode  json = maper.readTree(resultStr);
				JsonNode  son=json.get("result");
				JsonNode  js = maper.readTree(son.toString());
				for (int i = 0; i < js.size(); i++) {
					System.out.println(js.size());
					JsonNode  jsons=js.get(i);
					Xmember xm= new Xmember();
					xm.setId(jsons.get("id").getTextValue());
					xm.setName(jsons.get("name").getTextValue());
					xm.setMainUserId(jsons.get("mainUserId").getLongValue());
					xm.setIdCard(jsons.get("idCard").getTextValue());
					xm.setPhone(jsons.get("phone").getTextValue());
					xm.setAddress(jsons.get("address").getTextValue());
					xm.setUrl(jsons.get("url").getTextValue());
					m.add(xm);
				}
				logger.info("----------------------------获取所有成员用户详情   请求成功-------------------------------");
			}
		} catch (Exception e) {
			logger.info("----------------------------获取所有成员用户详情     请求错误-------------------------------");
		} 
		logger.info("----------------------------获取所有成员用户详情   请求结束-------------------------------");
		Post.releaseConnection();
		return m;
	}
	
	
	public static List<Xmember> getByUserIdList(Long userId) {
		logger.info("----------------------------根据用户ID获取所有成员用户详情 start-------------------------------");
		String SET_EXPLORER = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.19) Gecko/20081202 Iceweasel/2.0.0.19 (Debian-2.0.0.19-0etch1)";
		PostMethod Post = null;
		List<Xmember> m= new ArrayList<Xmember>();
		try {
			HttpClient client = new HttpClient();
			client.getParams().setParameter(HttpMethodParams.USER_AGENT, SET_EXPLORER);
			client.getParams().setContentCharset("utf-8");
			client.getParams().setHttpElementCharset("utf-8");
			String url = mip+"/api/uc/getListMember";// 推荐
//			String url = "http://localhost:8877/api/uc/getListMember";
			Post = new PostMethod(url);
			Post.setRequestHeader("userId", userId+"");
			String resultStr = null;
			int result = 0;
			result = client.executeMethod(Post);
			if (result == HttpStatus.SC_OK) {
				resultStr = Post.getResponseBodyAsString();// 成功响应请求 获取响应结果
				JsonNode  json = maper.readTree(resultStr);
				JsonNode  son=json.get("result");
				JsonNode  js = maper.readTree(son.toString());
				for (int i = 0; i < js.size(); i++) {
					JsonNode  jsons=js.get(i);
					Xmember xmb= new Xmember();
					xmb.setId(jsons.get("id").getTextValue());
					xmb.setName(jsons.get("name").getTextValue());
					xmb.setMainUserId(jsons.get("mainUserId").getLongValue());
					xmb.setIdCard(jsons.get("idCard").getTextValue());
					xmb.setPhone(jsons.get("phone").getTextValue());
					xmb.setAddress(jsons.get("address").getTextValue());
					xmb.setUrl(jsons.get("url").getTextValue());
					m.add(xmb);
				}
				logger.info("----------------------------根据用户ID获取所有成员用户详情   请求成功-------------------------------");
			}
		} catch (Exception e) {
			logger.info("----------------------------根据用户ID获取所有成员用户详情     请求错误-------------------------------");
		} 
		logger.info("----------------------------根据用户ID获取所有成员用户详情   请求结束-------------------------------");
		Post.releaseConnection();
		return m;
	}
	
//	public static List<XlinkMan> getByUserIdLinkman(Long userId) {
//		logger.info("----------------------------根据用户ID获取所有联系人详情 start-------------------------------");
//		String SET_EXPLORER = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.8.1.19) Gecko/20081202 Iceweasel/2.0.0.19 (Debian-2.0.0.19-0etch1)";
//		PostMethod Post = null;
//		List<XlinkMan> ml= new ArrayList<XlinkMan>();
//		try {
//			HttpClient client = new HttpClient();
//			client.getParams().setParameter(HttpMethodParams.USER_AGENT, SET_EXPLORER);
//			client.getParams().setContentCharset("utf-8");
//			client.getParams().setHttpElementCharset("utf-8");
//			String url = mip+"/api/uc/getListLinkMan";// 推荐
////			String url = "http://localhost:8877/api/uc/getListLinkMan";
//			Post = new PostMethod(url);
//			Post.setRequestHeader("userId", userId+"");
//			String resultStr = null;
//			int result = 0;
//			result = client.executeMethod(Post);
//			if (result == HttpStatus.SC_OK) {
//				resultStr = Post.getResponseBodyAsString();// 成功响应请求 获取响应结果
//				JsonNode  json = maper.readTree(resultStr);
//				JsonNode  son=json.get("result");
//				JsonNode  js = maper.readTree(son.toString());
//				for (int i = 0; i < js.size(); i++) {
//					JsonNode  jsons=js.get(i);
//					XlinkMan xlk= new XlinkMan();
//					xlk.setId(jsons.get("id").getTextValue());
//					xlk.setName(jsons.get("name").getTextValue());
//					xlk.setMainUserId(jsons.get("mainUserId").getLongValue());
//					xlk.setLogo(jsons.get("logo").getTextValue());
//					xlk.setPhone(jsons.get("phone").getTextValue());
////					xlk.setIsDelete(Integer.valueOf(jsons.get("isDelete").getTextValue()));
//					ml.add(xlk);
//				}
//				logger.info("----------------------------根据用户ID获取所有联系人详情   请求成功-------------------------------");
//			}
//		} catch (Exception e) {
//			logger.info("----------------------------根据用户ID获取所有联系人详情     请求错误-------------------------------");
//		} 
//		logger.info("----------------------------根据用户ID获取所有联系人详情   请求结束-------------------------------");
//		Post.releaseConnection();
//		return ml;
//	}
//	public static void main(String[] args) {
//		getPayUserId("1_108");
//	}
}
