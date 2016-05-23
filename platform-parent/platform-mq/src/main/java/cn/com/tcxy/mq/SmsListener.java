package cn.com.tcxy.mq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.tcxy.sms.SmsSenderImpl;

/**
 * 从MQ接收短信，然后发送短信到终端
 * 
 * @author johnny wang
 * 
 */
@Component
public class SmsListener implements MessageListener {

    private static Logger LOGGER = LoggerFactory.getLogger(SmsListener.class);

    @Autowired
    private SmsSenderImpl smsSender;

    @Override
    public void onMessage(final Message message) {

        if (message instanceof MapMessage) {
            try {
                MapMessage mapMessage = (MapMessage) message;

                mapMessage.getJMSTimestamp();

                String action = mapMessage.getString("action");
                String mobile = mapMessage.getString("mobile");
                String content = mapMessage.getString("content");
                LOGGER.info("SmsSenderListener开始消费-->send to" + mobile
                        + ", content-->" + content);
                if (action.equalsIgnoreCase("verificationCode")) {
                    String result = smsSender.sendVerificationCode(mobile, content);
                    LOGGER.info("短信发送结果：{}，接收人：{},内容：{}", result, mobile, content);
                }
            } catch (JMSException e) {
                LOGGER.error("接收JMS队列消息出错, error:{}", e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(
                    "Message must be of type TextMessage");
        }
    }

}
