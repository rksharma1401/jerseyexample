package com.study.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.study.vo.User;

@Path("userService")
public class UserService {

    @GET
    @Path("{userName}")
    public User getOrders(@PathParam("userName") String userName) {
    	User user=new User(userName);
    	user.setCompany("Test"); 
    	user.setPost("Dev");
        return user;
    }

    
}

