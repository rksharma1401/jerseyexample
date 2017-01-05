/**
 * 
 */
package com.study.client;

import org.glassfish.jersey.client.ChunkedInput;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class RunClient {

	public static void main(String args[]) throws InterruptedException {
		Client client = ClientBuilder.newClient();

		final Response response = client.target("http://jerseyexample-ravikant.rhcloud.com/rest/jws/streaming").request()
				.get();
		final ChunkedInput<String> chunkedInput = response.readEntity(new GenericType<ChunkedInput<String>>() {
		});
		String chunk;
		while ((chunk = chunkedInput.read()) != null) {
			System.err.println("Next chunk received: " );
			System.out.println(chunk.substring(0,10));
		}
		 Thread.sleep(10000000);

		 
	}
}
