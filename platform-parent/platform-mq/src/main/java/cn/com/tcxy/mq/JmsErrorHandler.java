package cn.com.tcxy.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component("listenerErrorHandler")
public class JmsErrorHandler implements ErrorHandler {
    private static Logger LOGGER = LoggerFactory
            .getLogger(JmsErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        LOGGER.error(t.getMessage());
    }

}
