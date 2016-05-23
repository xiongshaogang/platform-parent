package com.dvn.telemedicine.util;
public class DbContextHolder {
	    private static final ThreadLocal<String> holder = new ThreadLocal<String>();  
      
	    public static void putDataSourceName(String name){  
	        holder.set(name);  
	    }  
	      
	    public static String getDataSourceName(){  
	        return holder.get();  
	    } 
	    
	    public static void clear() {    
	    	holder.remove();    
	    }  

}
