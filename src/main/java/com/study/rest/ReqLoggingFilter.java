/**
 * 
 */
package com.study.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
 

/**
 * @author ravikant.sharma
 * 20-Jan-2017
 */
public class ReqLoggingFilter implements ClientRequestFilter {
    private static final Logger LOG = Logger.getLogger(ReqLoggingFilter.class.getName());

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
    	try{
        LOG.log(Level.ALL, requestContext.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
}