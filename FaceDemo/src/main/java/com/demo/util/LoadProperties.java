package com.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.springframework.util.ResourceUtils;

public class LoadProperties {
	public static String loadProperties(String fileName,String name) {  
	    Properties properties = new Properties();  
	    String value="";
	  
	    try {  
	    	File cfgFile = ResourceUtils.getFile("classpath:conf/"+fileName); 
	  	    InputStream stream =  new FileInputStream(cfgFile);
	  	     
	        properties.load(new InputStreamReader(stream, "utf-8"));  
	        value = properties.getProperty(name).trim();  
	    } catch (IOException e) {  
	        // TODO Auto-generated catch block  
	        e.printStackTrace();  
	    }  
	    return value;  
	} 
}
