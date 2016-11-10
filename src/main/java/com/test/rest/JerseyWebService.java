/**
 * 
 */
package com.test.rest;


import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
 
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
	
	@GET
	@Path("getMap")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMap() { 
		Map<String, String> outputMap=new HashMap<String, String>();
		outputMap.put("1", "One");
		outputMap.put("2", "two"); 
		Gson gson=new Gson();
		String jsonString = gson.toJson(outputMap);
	    return jsonString; 

	}

}