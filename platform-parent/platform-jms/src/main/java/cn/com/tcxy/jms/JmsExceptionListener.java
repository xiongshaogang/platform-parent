package cn.com.tcxy.jms;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class JmsExceptionListener implements ExceptionListener {
    private static Logger LOGGER = LoggerFactory
            .getLogger(JmsExceptionListener.class);

    @Override
    public void onException(final JMSException e) {
        LOGGER.error("come into onException method" + e);
    }
}
