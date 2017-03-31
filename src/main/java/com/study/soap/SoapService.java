package com.study.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class SoapService {

	@WebMethod
	public String sayHello() {
		return "Hello";
	}
	
}
