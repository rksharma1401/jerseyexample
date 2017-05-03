package com.study.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateConnection {
	static SessionFactory sessionFactory = null;
	static {
		try {
			AnnotationConfiguration configuration = new AnnotationConfiguration();
			configuration.configure("hibernate.cfg.xml");
			System.out.println("Hibernate Configuration created successfully");
			sessionFactory = configuration.buildSessionFactory();
			System.out.println("SessionFactory created successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public HibernateConnection() {
	}

	public static Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
