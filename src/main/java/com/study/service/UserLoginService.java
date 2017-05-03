/**
 * 
 */
package com.study.service;

import org.hibernate.Session;

import com.study.db.HibernateConnection;
import com.study.vo.User;

/**
 * @author ravikant.sharma Nov 11, 2016
 */
public class UserLoginService {

	
	public static User createUser(User u){
		System.out.println("inside createUser");
		 
		Session session=HibernateConnection.getSession();
		u=(User) session.save(u);
		return u;
	}
	
	public static boolean isValid(int id) throws InterruptedException {
		System.out.println("inside validation");
		 
		Session session=HibernateConnection.getSession();
		
		User user=(User) session.get(User.class, id);
		 if(user!=null){
			 return true; 
		 }
		return false;
	}

	 
}
