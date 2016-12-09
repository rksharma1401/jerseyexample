/**
 * 
 */
package com.test.client;

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
import com.test.vo.User;

import rx.functions.Action1;

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
		{
			String url = "http://jerseyexample-ravikant.rhcloud.com/rest/jws/getObj/list";
			System.out.println(url);
			Response response = client.target(url).request().get();
			ObjectMapper ob = new ObjectMapper();

			Object pojos = response.readEntity(Object.class);
			System.out.println("Receved instance of " +pojos.getClass());
			 if(pojos instanceof java.util.ArrayList){
				ArrayList<User> list =  (ArrayList) pojos;
				 
				for (Object pojo : list) {
					User user = ob.convertValue(pojo, User.class);
					System.out.println(user.getName());
				}
			}else{
				System.out.println("user");
				User user = ob.convertValue(pojos, User.class);
				System.out.println(user.getName());
			} 
			 System.exit(0);
		}
		
			
			
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

	 
		 
		final long startTime = new Date().getTime();

		responseFuture = client.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/test401withcontent")
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
						// throwable.printStackTrace();
						System.out.println("is isCancelled " + responseFuture.isCancelled());
					}

				});

		// System.out.println(responseFuture.cancel(true));

		Future<String> future1 = client.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/test401withcontent")
				.request().async().get(String.class);
		try {
			System.err.println("future1 Response" + future1.get(6000, TimeUnit.MILLISECONDS));

		} catch (Exception e) {
			System.err.println(e.getMessage());
			future1.cancel(true); // this method will stop
									// running underlying
									// task
		}

		/*
		 * Future<Response> response = ClientBuilder.newClient()
		 * .target("http://example.com/resource") .request() .async() .get();
		 */

		System.out.println("Main");
		
		
		String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=" 
	            + "en" + "&tl=" + "hi" + "&dt=t&q=" +  URLEncoder.encode("Hello | how are you");
	   System.out.println(url);
		  Response response = client.target(
				  url
		  ). request().get();
		  //[[["नमस्कार कैसे","Hello how",,,1]],,"en"]
		  String res=response.readEntity(String.class);
		  System.out.println(res);
		  	res= res.replaceAll(Pattern.quote("["), "")
				  .replaceAll(Pattern.quote("\""), "")
				  .split(",")[0]
				  ;
		    System.out.println(res);
		  
		  System.exit(0);
	}

}
