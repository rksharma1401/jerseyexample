/**
 * 
 */
package com.study.service;

/**
 * @author ravikant.sharma Nov 11, 2016
 */
public class UserLoginService {

	public static boolean isValid(int id) throws InterruptedException {
		System.out.println("inside validation");
		Thread.sleep(5000);
		if (id > 0)
			return true;
		else
			return false;
	}

	 
}
