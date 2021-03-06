/**
 * 
 */
package com.study.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import javax.xml.ws.http.HTTPException;
 
@Provider
public class ExceptionConvertor  extends WebApplicationException implements ExceptionMapper<Exception>
{ 
   
	private static final long serialVersionUID = -2468481774544020450L;

	@Override
	public Response toResponse(Exception exception) {

		
		StringBuilder response = new StringBuilder("<response>");
        response.append("<status>ERROR</status>");
        response.append("<message>"+ exception.getMessage()+"</message>");
        response.append("<time>" + new Date().toString() + "</time>");
        response.append("</response>"); 
        if(exception instanceof HTTPException){
        	HTTPException httpException=(HTTPException) exception;
        	return Response.status(httpException.getStatusCode()).build();
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return Response.serverError().entity(sw.toString()).type(MediaType.TEXT_HTML).build();
	
	}

	  
}