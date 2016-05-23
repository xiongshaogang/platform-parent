package com.dvn.uc.client.plugin;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dvn.telemedicine.common.api.dto.CallBackModelDTO;
import com.dvn.telemedicine.util.AppKeys;

@Component
@Aspect // 声明切面  
public class AccessAspect {  
	private static Logger logger = LoggerFactory.getLogger(AccessAspect.class);
	//@Autowired
	//private TelemedicineMessageProducer telemedicineMessageProducer;
	//private ObjectMapper maper = new ObjectMapper();
    // 声明切入点  
    @Pointcut("execution(* com.dvn.telemedicine.common.api..*.*(..))")  
    public void anyMethod(){}  
 
    // 拦截对目标对象方法调用  
    // 环绕
    //@Around("com.crane.aspect.LoggerAspect.aroundPointCut()")  
    @Around("anyMethod()")  
    public Object doLoggerPointCut(ProceedingJoinPoint jp) throws Throwable {  
    	
    	logger.info("------------------------数据拦截   开始--------------------------");
        // 获取连接点的方法签名对象  
        MethodSignature joinPointObject = (MethodSignature) jp.getSignature();  
        // 连接点对象的方法  
        Method method = joinPointObject.getMethod();  
        // 连接点方法方法名  
        String name = method.getName();  
        Class<?>[] parameterTypes = method.getParameterTypes();  
        // 获取连接点所在的目标对象  
        Object target = jp.getTarget();  
        // 获取目标方法  
        method = target.getClass().getMethod(name, parameterTypes);  
        // 返回@AroundPointCut的注释对象  
        AccessPointCut joinPoint = method.getAnnotation(AccessPointCut.class);  
        if(joinPoint!=null){
        	if(joinPoint.accessRead()){
        		  Object obj[] = jp.getArgs();
        		  //Long uid=Long.parseLong(obj[0].toString());
        		  HttpServletRequest request=(HttpServletRequest)obj[obj.length-1];
        		  logger.info("--访问IP:------"+request.getRemoteAddr());
        		  //logger.info("--当前用户ID:--"+SpringSecurityUtils.getCurrentUser().getId());
        		  String userId=request.getHeader(AppKeys.USER_ID);//用户ID
    		      String ticketId=request.getHeader(AppKeys.TICKET_ID);//票据ID
    		      String hardwareId= request.getHeader(AppKeys.STBOC_CODE);// 机顶盒硬件码
    		      CallBackModelDTO<String> callback=null;
        		  //WEB
//        		  if(userId == null || "".equals(userId) || hardwareId==null || "".equals(hardwareId) || ticketId==null || "".equals(ticketId)){
//        			  logger.info("------------------------数据拦截    数据错误！--------------------------");
//        			  //callback=new CallBackModelDTO<String>("请重新注册",null,"501");
//        			  return new CallBackModelDTO<String>("请重新注册",null,"501");
//        		  }
        		  //else{//手机
//        			  CheckRTicket ch=new CheckRTicket();
//        			  CallBackModelDTO<String> to=ch.checkTicket(ticketId, Long.valueOf(userId), hardwareId);
//        			  String code=to.getCode();
//        			  if(code!=null && !("").equals(code)){
//        				  if("501".equals(code)){
//        					  callback=new CallBackModelDTO<String>("请重新注册",null,"501");
//        				  }else if ("502".equals(code)){
//        					  callback=new CallBackModelDTO<String>("请重新登录",null,"502");
//        				  }else{
//	        				  //request.setAttribute(AppKeys.APPROVE,  to);
//        					  Path path=method.getAnnotation(javax.ws.rs.Path.class);
//        					  Map<String, String> content=new HashMap<String, String>();
//        					  content.put("method_name", name);
//        					  content.put("visit_path", path.value());
//        					  content.put("request_method", request.getMethod());
//        					  content.put("user_id", userId);
//        					  logger.info("----------------得到IP:"+request.getHeader("X-real-ip")+"----------------");
//        					  content.put("request_ip", request.getHeader("X-real-ip")+"");
//        					  content.put("stbox_code", hardwareId);
//        					  if(obj.length>1){//<=1表示只有header
//        						  String args="";
//        						  for(int i=0;i<obj.length-1;i++){
//        							  if(obj[i]!=null){
//        								  args+=obj[i]+",";
//        							  }
//        						  }
//        						  content.put("arg_content", args);
//        					  }else{
//        						  content.put("arg_content", "");
//        					  }
//        					  //sendLogMessage(content);
//	            			  logger.info("------------------------数据拦截   通过--------------------------");
//	            			  return jp.proceed();  
//	        			  }
//        			  }else{
//        				  callback=new CallBackModelDTO<String>("请重新注册",null,"501");
//        			  }
      //  		  }
        //		  return callback;
        	 }
        }
        logger.info("------------------------数据拦截   结束--------------------------");
        return jp.proceed();  
    }  
    
    @Before("anyMethod() && args(id)")//定义前置通知,anyMethod()是必须的方法，//args（username）不是必须的，是接收方法的参数  
    public void doAccessCheck(Long id) {  
          System.out.println("前置通知"+id);  
    }  
    
    @AfterReturning(pointcut="anyMethod()",returning="revalue")//定义后置通知 returning参数是接收被拦截的方法的返回值  
    public void doReturnCheck(CallBackModelDTO<Object> revalue) {  
              System.out.println("后置通知"+revalue);  
    }  
    
    @AfterThrowing(pointcut="anyMethod()", throwing="ex")//定义例外通知  
    public void doExceptionAction(Exception ex) {  
             System.out.println("例外通知"+ex);  
    } 
    //发日志信息
    private void sendLogMessage(Map<String, String> content){
    	//telemedicineMessageProducer.sendQueue(content);
    }
  //  @After("anyMethod()")//定义最终通知  
    //public void doReleaseAction() {  
     //        System.out.println("最终通知");  
    //}  
} 

