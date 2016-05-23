package com.dvn.telemedicine.common.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dvn.telemedicine.dao.MonitorTestDao;

@Component
@Path("/user")  
public class UserResourceService {

	@Autowired
	private MonitorTestDao monitorTestDao;

	@SuppressWarnings("finally")
	@GET
	@Path("/")
	@Produces( { MediaType.APPLICATION_JSON })
	public String test() {
		String code = null;
		try {
			String id = monitorTestDao.findUniqueBy("monitorValue", "ok").getId().toString();
			if(id!=null){
				code = "200";
			}
		} catch (Exception e) {
			code = "500";
			e.printStackTrace();
		}finally{
			return code;
		}
	}
	
}
