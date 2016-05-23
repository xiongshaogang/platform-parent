package cn.com.tcxy.mail;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import cn.com.tcxy.exception.BusinessException;
import cn.com.tcxy.mapper.MailLogMapper;
import cn.com.tcxy.mapper.MailVerificationLogMapper;
import cn.com.tcxy.model.MailLog;
import cn.com.tcxy.model.MailVerificationLog;
import cn.com.tcxy.model.MailVerificationLogExample;
import cn.com.tcxy.template.FreeMarkerTemplate;
import cn.com.tcxy.util.DateUtil;

/**
 * @author johnny wang
 * 
 */
@Component
public class MailServiceImpl implements MailService {

    private static Logger LOGGER = LoggerFactory
            .getLogger(MailServiceImpl.class);

    @Value("#{ mailProperties['mail.from'] }")
    private String mailFromName;

    @Autowired
    @Qualifier("mailTaskExecutor")
    private TaskExecutor taskExecutor;
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailLogMapper mailLogMapper;

    @Autowired
    private MailVerificationLogMapper mailVerificationLogMapper;

    @Autowired
    private FreeMarkerTemplate freeMarkerTemplate;
 

    public Boolean sendWelcomeMail(final String to, final String nickname) {
        LOGGER.info("发送邮件");

        String subject = "欢迎注册小云健康网站";

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nickname", nickname);
        model.put("currentDate", new Date().toString());
        final SimpleMailMessage msg = constructMail("welcome", model, to, subject);
        mailSender.send(msg);
        
        return false;
    }

    @Override
    public void sendForgotMail(String mailTo, String loginName,
            String validationUrl) {
    	
        Map<String, Object> args = new HashMap<String, Object>();
        String createTime = DateUtil.formatStandardDatetime(new Date());
        args.put("MEMBER_NAME", loginName);
        args.put("CREATE_TIME", createTime);
        args.put("ROOT_URI", validationUrl);
        final SimpleMailMessage msg = constructMail("forgot-mail", args, mailTo, "发送邮件" + validationUrl);
        this.mailSender.send(msg);
        
    }

    @Override
    public void sendNewOrderMail(String email, String orderCode) {
    	
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("orderCode", orderCode);
        final SimpleMailMessage msg = constructMail("new-order", args, email, "发送邮件");
        this.mailSender.send(msg);
        
    }

    @Override
    public Boolean sendVerificationCode(String email, String verificationType) {
    	
        MailVerificationLog mailLog = new MailVerificationLog();
        mailLog.setEmail(email);
        mailLog.setCreateTime(new Date());
        mailLog.setDeadTime(DateUtils.addDays(new Date(), 1));
        mailLog.setVerifyCode(RandomStringUtils.randomNumeric(6));
        mailLog.setMethod(verificationType);
        mailVerificationLogMapper.insert(mailLog);

        try {
            Map<String, Object> args = new HashMap<String, Object>();
            String createTime = DateUtil.formatStandardDatetime(new Date());
            args.put("CREATE_TIME", createTime);
            args.put("VERIFY_CODE", mailLog.getVerifyCode());
            final SimpleMailMessage msg = constructMail("mail-verification", args, email, "发送邮件");
            this.mailSender.send(msg);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public Boolean verifyEmailCode(String email, String verifyCode,
            String method) {
        
        MailVerificationLogExample example = new MailVerificationLogExample();
        MailVerificationLogExample.Criteria c1 = example.createCriteria();
        c1.andEmailEqualTo(email);
        c1.andVerifyCodeEqualTo(verifyCode);
        c1.andMethodEqualTo(method);
        c1.andDeadTimeGreaterThan(new Date());
        List<MailVerificationLog> result = mailVerificationLogMapper.selectByExample(example);
        if (result != null && !result.isEmpty()) {
        	return true;
        }
        return false;
    }

    @Override
    public Boolean isPermittedSendVerificationCode(String email, String method) {
    	// 查找1分钟之内发送的短信
    	Date date = DateUtils.addMinutes(new Date(), -1);
        MailVerificationLogExample example = new MailVerificationLogExample();
        MailVerificationLogExample.Criteria c1 = example.createCriteria();
        c1.andEmailEqualTo(email);
        c1.andMethodEqualTo(method);
        c1.andCreateTimeGreaterThan(date);
        
        List<MailVerificationLog> result = this.mailVerificationLogMapper.selectByExample(example);
        
        if (result != null && !result.isEmpty()) {
        	return false;
        }
        return true;		
        
    }
    private SimpleMailMessage constructMail(String template,
            Map<String, Object> params, String to, String subject)  {

        String content = freeMarkerTemplate.loadTemplate("mail"+File.separator+template+".ftl",
                params);

        final SimpleMailMessage msg = new SimpleMailMessage();
        try {
            // insertMail(from, to, subject, content, new Date());
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(content);
            msg.setFrom(this.mailFromName);
        } catch (MailException e) {
            throw new BusinessException(e.getMessage());
        }

        return msg;
    }
    
    
    @Override
    public Boolean sendApiVerificationEmail(String email,String title,String content) {
        try {
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("CONTENT", content);
            final SimpleMailMessage msg = constructMail("email-api-verification", args, email, title);
            this.mailSender.send(msg);
        } catch (Exception e) {
        	e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private void executeMailTask(final SimpleMailMessage msg) {
        taskExecutor.execute(new Runnable() {
            public void run() {
                mailSender.send(msg);
            }
        });
    }

}
