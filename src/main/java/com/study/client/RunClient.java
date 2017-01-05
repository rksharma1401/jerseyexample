/**
 * 
 */
package com.study.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ChunkedInput;

public class RunClient {

	public static void main(String args[]) throws InterruptedException, Exception {
		Client client = ClientBuilder.newClient();

		final Response response = client.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/streaming/3/1").request()
				.get();
		final ChunkedInput<String> chunkedInput = response.readEntity(new GenericType<ChunkedInput<String>>() {
		});
		String chunk;
		while ((chunk = chunkedInput.read()) != null) {/*
			System.err.println("Next chunk received: " );
			System.out.println(chunk);
		*/}
		
		{
			String uri = "http://jerseyexample-ravikant.rhcloud.com/rest/jws/streaming/3/2";
			URL url = new URL(uri);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				System.err.println("Next chunk received: " );
				System.out.println(line);
			}
			in.close();
		}

		 
	}
}
