/**
 * 
 */
package com.study.client;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.rx.Rx;
import org.glassfish.jersey.client.rx.rxjava.RxObservableInvoker;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.vo.User;

import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;

@SuppressWarnings("all")
class ListUser extends ArrayList {}

/**
 * @author ravikant.sharma Nov 18, 2016
 */
@SuppressWarnings("all")
public class ClientAsync {

	
	static Future<Response> responseFuture = null;
	static long betweenTime = 0;

	public static void main(String[] args) throws Exception {
		Client client = ClientBuilder.newClient();
//		{s
//			String url = "http://jerseyexample-ravikant.rhcloud.com/rest/jws/getObj/list";
//			System.out.println(url);
//			Response response = client.target(url).request().get();
//			ObjectMapper ob = new ObjectMapper();
//
//			Object pojos = response.readEntity(Object.class);
//			System.out.println("Receved instance of " +pojos.getClass());
//			 if(pojos instanceof java.util.ArrayList){
//				ArrayList<User> list =  (ArrayList) pojos;
//				 
//				for (Object pojo : list) {
//					User user = ob.convertValue(pojo, User.class);
//					System.out.println(user.getName());
//				}
//			}else{
//				System.out.println("user");
//				User user = ob.convertValue(pojos, User.class);
//				System.out.println(user.getName());
//			} 
//			 System.exit(0);
//		}
		
			
		/*	
		rx.Observable<Response> observable = Rx.newClient(RxObservableInvoker.class)
//				.target("http://javaresteasydemo-ravikant.rhcloud.com/rest/hello/getDataNoZip/")
				.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/getDataAsClient")
				.register(JacksonFeature.class).request().header("key", "12345").rx().get();
		
		observable.subscribe(new Action1<Response>() {

			@Override
			public void call(Response response) {
				try {
					System.out.println(" Inside call ");
					// System.out.println(response.readEntity(List.class));
					//List<java.util.LinkedHashMap> lst = response.readEntity(List.class);
					ObjectMapper ob = new ObjectMapper();
					List<User> pojos = ob.convertValue(response.readEntity(List.class), new TypeReference<List<User>>() {
					});
					for (User user : pojos) {
						System.out.println(user.getPost());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.exit(0);
			}

		});
		Thread.sleep(50 * 1000);
		System.exit(1);

	 */
		 
		final long startTime = new Date().getTime();

		responseFuture = client.target("https://jerseyexample-ravikant.rhcloud.com/rest/jws/test401withcontent")
				.request().async().get(new InvocationCallback<Response>() {

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
						System.out.println("is isCancelled " + responseFuture.isCancelled());
						if(responseFuture.isCancelled()){responseFuture.cancel(true);}
					}

				});

		 
		Thread.sleep(1 * 1000);
		if(!responseFuture.isDone())
		System.out.println("Request Cancelled for not receiving response in time :" +responseFuture.cancel(true));
		
		
		responseFuture = client.target("https://jerseyexample-ravikant.rhcloud.com/rest/jws/getError")
				.request().async().get(new InvocationCallback<Response>() {

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
						System.out.println("is isCancelled " + responseFuture.isCancelled());
						if(responseFuture.isCancelled()){responseFuture.cancel(true);}
					}

				});

		 
		Thread.sleep(1 * 1000);
		if(!responseFuture.isDone())
		System.out.println("Request Cancelled for not receiving response in time :" +responseFuture.cancel(true));
		
		
		
//		Future<String> future1 = client.target("https://jerseyexample-ravikant.rhcloud.com/rest/jws/getError")
//				.request().async().get(String.class);
//		try {
//			System.err.println("future1 Response" + future1.get(6000, TimeUnit.MILLISECONDS));
//
//		} catch (Exception e) {
//			System.err.println(e.getMessage());
//			future1.cancel(true); // this method will stop running underlying task
//		}
		
		Thread.sleep(5 * 1000);
		System.exit(0);
	}
}
