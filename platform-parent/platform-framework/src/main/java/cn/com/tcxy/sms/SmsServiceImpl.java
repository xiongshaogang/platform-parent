package cn.com.tcxy.sms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import cn.com.tcxy.mapper.SmsVerificationLogMapper;
import cn.com.tcxy.model.SmsVerificationLog;
import cn.com.tcxy.model.SmsVerificationLogExample;
import cn.com.tcxy.model.base.BaseSmsVerificationLogExample.Criteria;
import cn.com.tcxy.template.FreeMarkerTemplate;
import cn.com.tcxy.util.DateUtil;
import cn.com.tcxy.util.StringUtil;
import cn.com.tcxy.util.Utils;

/**
 * @author johnny wang
 * 
 */
@Component
public class SmsServiceImpl implements SmsService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SmsServiceImpl.class);

    /**
     * 发送地址url
     */
    @Value("#{ smsProperties['sms.url'] }")
    private String smsUrl;

    /**
     * 企信通帐号
     */
    @Value("#{ smsProperties['sms.username'] }")
    private String username;

    /**
     * 企信通密码
     */
    @Value("#{ smsProperties['sms.password'] }")
    private String password;
    
    /**
     * 密匙
     */
    @Value("#{ smsProperties['sms.apikey'] }")
    private String smsApikey;

    /**
     * 发送方法
     */
    @Value("#{ smsProperties['sms.method'] }")
    private String smsMethod;

    @Autowired
    private FreeMarkerTemplate freeMarkerTemplate;

    @Autowired
    @Qualifier("smsTaskExecutor")
    private TaskExecutor taskExecutor;

    @Autowired
    private SmsVerificationLogMapper smsVerificationLogMapper;

    @Override
    public String sendVerificationCode(final String mobile,
            final String verificationType) {

        final SmsVerificationLog smsLog = new SmsVerificationLog();
        smsLog.setPhone(mobile);
        Date now = new Date();
        smsLog.setCreateTime(now);
        //smsLog.setDeadTime(DateUtil.addDay(now, 1));
        smsLog.setDeadTime(DateUtils.addDays(new Date(), 1));
        String verificationCode = RandomStringUtils.randomNumeric(6);
        smsLog.setVerifyCode(verificationCode);
        smsLog.setMethod(verificationType);

        this.smsVerificationLogMapper.insert(smsLog);

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("verificationCode", verificationCode);

        executeSmsTask(mobile, "verification-code", model);

        return verificationCode;
    }

    @Override
    public Boolean notifyNewOrder(final String mobile, final String orderCode) {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderCode", orderCode);

        executeSmsTask(mobile, "new-order", params);

        return true;
    }

    private SmsVerificationLog findSmsVerification(String mobile, String verifyCode,
            String method, Date verifyTime) {
        SmsVerificationLogExample example = new SmsVerificationLogExample();
        Criteria c1 = example.createCriteria();
        c1.andPhoneEqualTo(mobile);
        if (verifyCode != null) {
        	c1.andVerifyCodeEqualTo(verifyCode);
        }
        c1.andMethodEqualTo(method);
        //c1.andCreateTimeGreaterThan(now);
        c1.andDeadTimeGreaterThan(verifyTime);
        //example.getOredCriteria().add(c1);
        List<SmsVerificationLog> result = this.smsVerificationLogMapper.selectByExample(example);

        // TODO: 需要考虑返回多条记录的情况，取得最近的一条记录，所以上面的查询语句需要按时间排序
        if (null != result && result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Boolean verifySmsCode(String mobile, String verificationCode,
            String verificationType) {
        SmsVerificationLog smsLog = findSmsVerification(mobile, verificationCode,
                verificationType, new Date());
        Date now = new Date();
        if (null != smsLog && now.before(smsLog.getDeadTime())
                && now.after(smsLog.getCreateTime())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean isPermittedSendVerificationCode(String mobile,
            String method) {
        // 查找1分钟之内发送的短信
        //Date date = DateUtil.addMinute(new Date(), -1);
    	Date date = DateUtils.addMinutes(new Date(), -1);
        SmsVerificationLogExample example = new SmsVerificationLogExample();
        Criteria c1 = example.createCriteria();
        c1.andPhoneEqualTo(mobile);
        c1.andMethodEqualTo(method);
        c1.andCreateTimeGreaterThan(date);
        
        List<SmsVerificationLog> result = this.smsVerificationLogMapper.selectByExample(example);
        
        if (result != null && !result.isEmpty()) {
        	return false;
        }
        return true;		
    }
    /**
     * 执行短信发送.
     * @param mobile 手机号. 
     * @param template 模板名称,模板默认放在sms/xxx.ftl下,此名称为"xxx"部分.
     * @param params 模板中需要替换的参数键值对.
     */
    @Override
    public void executeSmsTask(final String mobile, final String template,
            final Map<String, Object> params) {
        final String content = this.freeMarkerTemplate.loadTemplate("sms/"
                + template + ".ftl", params);
        //现阶段短信发送量较小，因此暂时使用同步发送代替异步发送，
        //taskExecutor.execute(new Runnable() {
          //  public void run() {
        		sendSMS(mobile, content);
           // }
        //});
    }
    
    private void sendSms(String mobile, String content) {
        if (Utils.isNull(mobile)) {
            LOGGER.info("mobile can not be null");
            return;
        }

        String res = connectSMSServer(mobile,content);

        // TODO: 处理SMS发送的返回值，返回值格式是XML格式，暂时不处理，下面代码暂时不起作用。
        ArrayList<String[]> list = new ArrayList<String[]>();
        SAXReader saxReader = new SAXReader();
        Document doc = null;
        try {
            doc = saxReader.read(new ByteArrayInputStream(res.getBytes()));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = doc.getRootElement();
        List<Element> elements = root.elements("sid");
        for (Element sms : elements) {
            String[] csv = new String[3];
            csv[0] = DateUtil.formatStandardDatetime(new Date()); // time
            csv[1] = sms.getText(); // smsId
            csv[2] = content; // content
            list.add(csv);
        }
    }

    private String connectSMSServer(String mobile, String msg) {
        String content = "username=" + username + "&password=" + password
                + "&method=" + smsMethod + "&mobile=" + mobile + "&msg=" + msg;
        String recString = "";
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(smsUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(30000);
            urlConn.setReadTimeout(30000);
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            OutputStream out = urlConn.getOutputStream();
            out.write(content.getBytes("utf-8"));
            out.flush();
            out.close();
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream(), "gb2312"));
            StringBuffer sb = new StringBuffer();
            int ch;
            while ((ch = rd.read()) > -1) {
                sb.append((char) ch);
            }
            recString = sb.toString().trim();

            // rec_string = URLDecoder.decode(rec_string, "UTF-8");
            rd.close();
        } catch (Exception e) {
            recString = "-107";
            LOGGER.error(e.getMessage());
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
        return recString;
    }
       

    @Override
    public String sendSMS(String mobile, String msg) {
        //返回结果
    	String result = null;
    	
    	if(StringUtil.isEmpty(mobile)){
    	    return "error:Mobile phone number can't be empty";
    	}
    	if(StringUtil.isEmpty(msg)){
            return "error:The message content can't be empty";
        }
    	if(!StringUtil.isMobile(mobile)){
    	    return "error:Mobile phone number format is not correct";
    	}
    	
    	HttpURLConnection connection = null;
  		try {
	    	//发送内容
	  		String content = msg; 
	  		
	  		// 创建StringBuffer对象用来操作字符串
	  		StringBuffer connectStr = new StringBuffer(smsUrl);
	  		
	  		// APIKEY
	  		connectStr.append("apikey="+smsApikey);
	
	  		//用户名
	  		connectStr.append("&username="+username);
	
	  		// 向StringBuffer追加密码
	  		connectStr.append("&password="+password);
	
	  		// 向StringBuffer追加手机号码,多个号码用逗号隔开
	  		connectStr.append("&mobile="+mobile);
	
	  		// 向StringBuffer追加消息内容转URL标准码
			connectStr.append("&content="+URLEncoder.encode(content,"GBK"));
	
	  		// 创建url对象
	  		URL url = new URL(connectStr.toString());
		
	  		// 打开url连接
	  		connection = (HttpURLConnection) url.openConnection();
		
	  		// 设置url请求方式 ‘get’ 或者 ‘post’
			connection.setRequestMethod("POST");
		
	  		// 发送
	  		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
	  		// 返回发送结果
	  		result = in.readLine();
		} catch (Exception e) {
		    result = "error:"+e.getMessage();
            LOGGER.error(e.getMessage(),e);
        } finally {
            if (connection != null) {
            	connection.disconnect();
            }
        }

  		// 输出结果
  		//System.out.println(result);
  		
  		return result;
    }
    
    @Override
    public Boolean personalSendSMS(String cellphone,String content){
        String result = this.sendSMS(cellphone, content);
        if(result.startsWith("success")){
            return true;
        }
        return false;
    }
}