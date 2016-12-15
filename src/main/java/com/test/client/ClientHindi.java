/**
 * 
 */
package com.test.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * @author ravikant.sharma
 * 15-Dec-2016
 */
public class ClientHindi {

	public static void main(String[]args) { 
		Client client = ClientBuilder.newClient();
		{
			String url="https://jerseyexample-ravikant.rhcloud.com/rest/jws/toHindi/Hello";
			System.out.println(url);
			Response response = client.target(url).request().get();
			System.out.println("Response status code " + response.getStatus() + " received." + "\nMessage :"
					+ response.readEntity(String.class));
		}
	}
}
