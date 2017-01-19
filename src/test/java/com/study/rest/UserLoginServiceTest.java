/**
 * 
 */
package com.study.rest;

import static org.junit.Assert.*;

import org.junit.Test;

import com.study.service.UserLoginService;

/**
 * @author ravikant.sharma
 * 09-Jan-2017
 */
@SuppressWarnings("unused")
public class UserLoginServiceTest { 


	/**
	 * Test method for {@link com.study.service.UserLoginService#isValid(int)}.
	 * @throws InterruptedException 
	 */
	
	@Test
	public void testIsValidTrue() throws InterruptedException {
		
		int id=10;
		Boolean output=null;
		
		Given :
			assertTrue(id>0);
		When:
			output=UserLoginService.isValid(id);
		Then:
			assertTrue(output);	
			
	}
	
	
	@Test
	public void testIsValidFalse() throws InterruptedException {
		
		int id=0;
		Boolean output=null;
		
		
		Given :
			assertFalse(id>0);
		When:
			output=UserLoginService.isValid(id);
		Then:
			assertFalse(output);	
		
			
	}

}
