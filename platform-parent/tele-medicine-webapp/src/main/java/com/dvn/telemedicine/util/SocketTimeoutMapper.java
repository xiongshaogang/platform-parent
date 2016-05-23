package com.dvn.telemedicine.util;

import java.net.SocketTimeoutException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Provider
public class SocketTimeoutMapper implements ExceptionMapper<SocketTimeoutException>{
	
	@Override
	public Response toResponse(SocketTimeoutException e) {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("Read timed out.");
        return Response.status(Status.BAD_REQUEST).build();

    }
}
