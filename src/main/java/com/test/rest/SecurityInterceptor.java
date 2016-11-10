/**
 * 
 */
package com.test.rest;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext reqCtx, ContainerResponseContext respCtx) throws IOException {
		/*com.sun.research.ws.wadl.Response l = null; 
		l.getRepresentationOrFault();*/
		System.out.println("Adding start time in request headers");

		System.out.println("Adding ProcessingTime in response headers"); 
		long startTime = Long.parseLong(reqCtx.getHeaderString("startTime")); 
		respCtx.getHeaders().add("ProcessingTime",
				String.valueOf(System.currentTimeMillis() - startTime) + " millisecs");

	}

	@Override
	public void filter(ContainerRequestContext reqCtx) throws IOException {
		System.out.println("Adding start time in request headers");

		reqCtx.getHeaders().add("startTime", String.valueOf(System.currentTimeMillis()));

	}

}
