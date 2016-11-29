/**
 * 
 */
package com.test.client;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;

/**
 * @author ravikant.sharma Nov 18, 2016
 */
public class ClientAsync {

	
	static Future<Response> responseFuture = null;
	static long betweenTime = 0;

	public static void main(String[] args) throws Exception {
		Client client = ClientBuilder.newClient();
		// System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
		/*
		 
		  Response response = client.target(
		  "http://jerseyexample-ravikant.rhcloud.com/rest/jws/test401withcontent").
		 request().get(); System.out.println(response.readEntity(String.class));
		 
		 System.exit(0); */
		final long startTime = new Date().getTime();

		responseFuture = client.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/test401withcontent").request().async()
				.get(new InvocationCallback<Response>() {

					@Override
					public void completed(Response response) {
						System.out.println("Response status code " + response.getStatus() + " received." + "\nMessage :"
								+ response.readEntity(String.class));
						betweenTime = new Date().getTime();
						System.out.println("Time Taken " + (betweenTime - startTime) + TimeUnit.MILLISECONDS);
					}

					@Override
					public void failed(Throwable throwable) {
						System.out.println("Invocation failed due to" + throwable.getMessage());
						// throwable.printStackTrace();
						System.out.println("is isCancelled " + responseFuture.isCancelled());
					}

				});

		// System.out.println(responseFuture.cancel(true));

		Future<String> future1 = client.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/test401withcontent").request()
				.async().get(String.class);
		try { 
			 System.err.println("future1 Response" + future1.get(6000, TimeUnit.MILLISECONDS));
			 
		} catch (Exception e) { 
			System.err.println(e.getMessage());
			future1.cancel(true); // this method will stop
														// running underlying
														// task
		}

		System.out.println("Main");
	}

}
