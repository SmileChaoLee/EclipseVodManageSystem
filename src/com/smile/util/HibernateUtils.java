package com.smile.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// a singleton class
public class HibernateUtils {
	
	private HibernateUtils() {
	}

	private static final SessionFactory sessionFactory = buildSessionFactory();
	
    private static SessionFactory buildSessionFactory() {
    		SessionFactory sessionFactory = null;
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        		Configuration config = new Configuration().configure("hibernate.cfg.xml");
	    		// Configuration config = new Configuration().configure();	
	        	sessionFactory = config.buildSessionFactory();
	        	System.out.println("HibernateUtils ---> buildSessionFactory() executed.");
        } catch (Throwable ex) {
            // Make sure you log the exception to track it
            System.err.println("SessionFactory creation failed." + ex);
            // throw new ExceptionInInitializerError(ex);
            ex.printStackTrace();
        }
        
		return sessionFactory;
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
   
    public static void shutdownSessionFactory() {
        // Optional but can be used to Close caches and connection pools
        // getSessionFactory().close();
    		sessionFactory.close();
    }
}
