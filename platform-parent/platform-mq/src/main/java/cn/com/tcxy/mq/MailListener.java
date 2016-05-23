package cn.com.tcxy.mq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.tcxy.mail.MailServiceImpl;

/**
 * 从MQ接收短信，然后发送短信到终端
 * 
 * @author johnny wang
 * 
 */
@Component
public class MailListener implements MessageListener {

    private static Logger LOGGER = LoggerFactory.getLogger(MailListener.class);

    @Autowired
    private MailServiceImpl mailService;

    @Override
    public void onMessage(final Message message) {

        if (message instanceof MapMessage) {
            MapMessage msg = (MapMessage) message;
            try {
                String action = msg.getString("action");

                String to = msg.getString("to");
                String nickname = msg.getString("nickname");

                if (action.equalsIgnoreCase("welcome")) {
                    mailService.sendWelcomeMail(to, nickname);
                } else {
                    LOGGER.info("not impelemented now: " + action);
                }
            } catch (JMSException e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException(
                    "Message must be of type TextMessage");
        }
    }

}
