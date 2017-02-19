package com.study.rest;/**
						* 
						*/

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.study.service.UserLoginService;

import mockit.Mock;
import mockit.MockUp;

/** 
 * @author ravikant.sharma Nov 9, 2016
 */
public class JerseyWebServiceTest extends JerseyTest {
	/*
	 * @Override protected URI getBaseUri() { return
	 * UriBuilder.fromUri("http://localhost/").port(8080).build(); }
	 */
 
	/*
	 * @Override protected DeploymentContext configureDeployment() {
	 * forceSet(TestProperties.CONTAINER_PORT, "0"); return
	 * ServletDeploymentContext.builder(new study.config.ApplicationConfig())
	 * .initParam(ServletProperties.JAXRS_APPLICATION_CLASS,
	 * JerseyWebServiceTest.class.getName()) .build(); }
	 */

 
	 

	@Override
	protected Application configure() {
		// enable(TestProperties.LOG_TRAFFIC);
		// enable(TestProperties.DUMP_ENTITY);
		return new ResourceConfig(JerseyWebService.class);
	}

	@Test
	public void testisUserValid() {
		new MockUp<UserLoginService>() {

			// Redefine the method here
			// But With No static modifier
			@Mock
			public boolean isValid(int id) throws InterruptedException {
				if (id > 0)
					return true;
				else
					return false;
			}

		};
		final Boolean response = target("jws/checkValidity/-1").request().get(Boolean.class);

		assertEquals(false, (Boolean) response);
		// when(UserLoginService.isValid(-1)).thenReturn(false);
		// when(UserLoginService.isValid(5)).thenReturn(false);

	}

	@Test
	public void testgetMap() {
		final String hello = target("jws/getMap").request().get(String.class);
		assertEquals("{\"1\":\"One\",\"2\":\"two\"}", hello);

	}

	@Test
	public void testInfo() {
		final Response hello = target("jws/").request().get(Response.class);
		assertEquals(200, hello.getStatus());
	}

}