package com.study.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.study.vo.User;

@Path("userService")
public class UserService {

    @GET
    @Path("{userName}")
    @Produces(MediaType.APPLICATION_XML)
    public User getOrders(@PathParam("userName") String userName) {
    	User user=new User(userName);
    	user.setCompany("Test"); 
    	user.setPost("Dev");
        return user;
    }

    
}

