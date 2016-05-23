package com.dvn.telemedicine.common.api.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CallBackModelDTO<T> implements java.io.Serializable{
  private  String message;
  private  T       result;
 // private  PageInfo page;
  
  //private  String header;
  
  private  String code;

	//public String getHeader() {
	//	return header;
	//}
	//public void setHeader(String header) {
		//this.header = header;
	//}
	public CallBackModelDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result= result;
	}
	//public PageInfo getPage() {
	//	return page;
	//}
	//public void setPage(PageInfo page) {
		//this.page = page;
	//}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public CallBackModelDTO(String message, T result, String code) {
		super();
		this.message = message;
		this.result = result;
		//this.page = page;
		this.code = code;
	}
	  
  
  
}
