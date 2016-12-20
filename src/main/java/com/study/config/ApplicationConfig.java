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

@ApplicationPath("/rest")
public class ApplicationConfig extends Application {

	
    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("jersey.config.server.provider.packages", "com.study.rest");
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