/**
 * 
 */
package com.test.rest;

import java.util.Date;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@Provider
public class EntityNotFoundException  extends WebApplicationException implements ExceptionMapper<NotFoundException>
{ 
 
	private static final long serialVersionUID = 7886520141629139380L;

	//@Override
	public Response toResponse(NotFoundException arg0) {
		
		StringBuilder response = new StringBuilder("<response>");
        response.append("<status>ERROR</status>");
        response.append("<message>The method you are looking for does not exist </message>");
        response.append("<time>" + new Date().toString() + "</time>");
        response.append("</response>"); 
        return Response.status(404).entity(response.toString()).type(MediaType.APPLICATION_XML).build();
	}

	  
}