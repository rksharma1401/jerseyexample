/**
 * 
 */
package com.study.rest;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
 

/**
 * @author ravikant.sharma
 * 20-Jan-2017
 */
public class ReqLoggingFilter implements ClientRequestFilter {
     
    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
    	try{
    			System.out.println(requestContext.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}