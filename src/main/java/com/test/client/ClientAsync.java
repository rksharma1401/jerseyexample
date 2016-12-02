/**
 * 
 */
package com.test.client;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.rx.Rx;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.test.vo.User;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * @author ravikant.sharma Nov 18, 2016
 */
@SuppressWarnings("all")
public class ClientAsync {

	 
	static Future<Response> responseFuture = null;
	static long betweenTime = 0;

	public static void main(String[] args) throws Exception {
		Client client = ClientBuilder.newClient();
		 
		rx.Observable<Response> observable = Rx.newClient(RxObservableInvoker.class)
		        .target("http://javaresteasydemo-ravikant.rhcloud.com/rest/hello/getDataNoZip/")
		        .register(JacksonFeature.class)
		        .request().header("key", "12345")
		        .rx()
		        .get();
 
		observable
	    .subscribe(new Action1<Response>() {

			@Override
			public void call(Response response) {
				try{
				System.out.println(" Inside call ");
			    	//System.out.println(response.readEntity(List.class));
					List<User> lst=response.readEntity(List.class);
			        for (User user :lst ) {
			        	System.out.println(user.getName());
			        }
				}catch(Exception e){
					e.printStackTrace();
				}
			        System.exit(0);
			}

			 
	    	
		});
		Thread.sleep(50*1000);
		 System.exit(0);
		
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
		
		/*Future<Response> response = ClientBuilder.newClient()
		        .target("http://example.com/resource")
		        .request()
		        .async()
		        .get();*/
		

		System.out.println("Main");
	}

}
