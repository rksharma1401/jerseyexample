/**
 * 
 */
package com.study.sendpulse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sendpulse.restapi.Sendpulse;

/**
 * @author ravikant.sharma
 * 22-Dec-2016
 */
public class PushDemo {
	 
	 private static final String API_CLIENT_ID = Messages.getString("PushDemo.API_CLIENT_ID");
	private static final String API_CLIENT_SECRET = Messages.getString("PushDemo.API_CLIENT_SECRET");

	public static void main(String[] args) { 
	  Sendpulse sendpulse = new Sendpulse(API_CLIENT_ID, API_CLIENT_SECRET);
	   
	  
	  Map<String, Object> taskinfo = new HashMap<>();
	Map<String, Object> additionalParams=new HashMap<>();
	taskinfo.put("title", "Welcome to JerseyExample");
	taskinfo.put("website_id", "26980"); 
	String newstring = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	taskinfo.put("body", "Sending from JAVA at :"+ newstring);
	taskinfo.put("ttl", "5"); 
	
	/*String filter="{
		  "variable_name": "id",
		  "operator": "or",
		  "conditions": [
		    {
		      "condition": "likewith",
		      "value": "624b-ac50-714b-e0b0-dad1-aeb7-8009-2fa1"
		    },
		    {
		      "condition": "notequal",
		      "value": "b"
		    }
		  ]
		}
	;*/
	Map<String, Object> response=sendpulse.createPushTask(taskinfo, null);
	
	System.out.println("result :"+response.get("data")); 
	System.out.println("is_error :" +response.get("is_error")); 
	System.out.println("http_code :" +response.get("http_code"));
	System.out.println(response);
	 }
	
}
