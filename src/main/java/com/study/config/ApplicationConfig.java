/**
 * 
 */
package com.study.config;
 

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/rest")
public class ApplicationConfig extends Application {

	 
    @Override
    public Map<String, Object> getProperties() {
    	
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("jersey.config.server.provider.packages", "com.study.rest");
        properties.put(CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER,"0");
        System.out.println("getProperties:-> CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER_SERVER :" + CommonProperties.getValue(properties,CommonProperties.OUTBOUND_CONTENT_LENGTH_BUFFER,String.class));
        return properties;
    }
      
    @PreDestroy
    public void preDestroy() {
      System.out.println("************************** @PreDestroy **************************************");
    }
    
    @PostConstruct
    public void postConstruct() {
      System.out.println("************************** @postConstruct **************************************"); 
    }
}