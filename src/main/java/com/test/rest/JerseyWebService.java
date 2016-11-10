/**
 * 
 */
package com.test.rest;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
 
/**
 * @author ravikant.sharma
 * Nov 9, 2016
 */
@Path("jws")
public class JerseyWebService {

	@GET
	@Path("say/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;

		return Response.status(200).entity(output).build();
		
	}
	
	@GET 
	@Path("")
	public Response info() throws MissingFileException {

		String output = "Hello from jersey !!!!!!!!!!!!!";
		//throw new 	MissingFileException();
		return Response.status(200).entity(output).build();
		
	}

}