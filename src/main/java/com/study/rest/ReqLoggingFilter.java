/**
 * 
 */
package com.study.rest;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.glassfish.jersey.filter.LoggingFilter;

/**
 * @author ravikant.sharma
 * 20-Jan-2017
 */
public class ReqLoggingFilter implements ClientRequestFilter {
    private static final Logger LOG = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        LOG.log(Level.ALL, requestContext.getEntity().toString());
    }
}