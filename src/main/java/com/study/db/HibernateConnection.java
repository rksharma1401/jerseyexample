package com.study.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
 

public class HibernateConnection {
	static SessionFactory sessionFactory =null;
	static{

		Configuration configuration = new Configuration();
    	configuration.configure("hibernate.cfg.xml");
    	System.out.println("Hibernate Configuration created successfully");
    	sessionFactory = configuration.buildSessionFactory();
    	System.out.println("SessionFactory created successfully");
    	 
	
	}
	public HibernateConnection(){}
	
	public static Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
}
