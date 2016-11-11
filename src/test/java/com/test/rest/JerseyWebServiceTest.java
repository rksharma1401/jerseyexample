package com.test.rest;/**
						* 
						*/

import static org.junit.Assert.assertEquals;

import java.net.URI;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

/**
 * @author ravikant.sharma Nov 9, 2016
 */
public class JerseyWebServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(JerseyWebService.class);
	}

	/*@Override
	protected URI getBaseUri() {
		return UriBuilder.fromUri("http://localhost/").port(8080).build();
	}*/

	/*@Override
	protected DeploymentContext configureDeployment() {
		forceSet(TestProperties.CONTAINER_PORT, "0");
		return ServletDeploymentContext.builder(new com.test.config.ApplicationConfig())
				.initParam(ServletProperties.JAXRS_APPLICATION_CLASS,
						JerseyWebServiceTest.class.getName())
				.build();
	}*/

	@Test
	public void testgetMap() {
		final String hello = target("jws/getMap").request().get(String.class); 
		assertEquals("{\"2\":\"two\",\"1\":\"One\"}", hello);

	}
	
	
	@Test
	public void testInfo() {
		final Response hello = target("jws/").request().get(Response.class); 
		assertEquals(200, hello.getStatus());

	}
}