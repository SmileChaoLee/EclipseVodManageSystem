package com.smile.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.smile.listeners.ServletContextListenerForVod;

public class HibernateUtils {
	
	private HibernateUtils() {
	}
	
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
 
    public static SessionFactory getStoredSessionFactory(HttpServletRequest request, boolean createYN) {
        
    		if (ServletContextListenerForVod.httpSessionHibernateFactoryMap == null) {
            return null;
        }
    		
    		System.out.println("httpSessionHibernateFactoryMap.size() = "+ServletContextListenerForVod.httpSessionHibernateFactoryMap.size());
        HttpSession session = request.getSession();
        SessionFactory factory = ServletContextListenerForVod.httpSessionHibernateFactoryMap.get(session);
        if (factory == null) {
            // no Hibernate SessionFactory (no database connection)
            System.out.println("no Hibernate SessionFactory for this session");
            if (createYN)
            {
                // needed to create a new Hibernate SessionFactory
                System.out.println("Creating a Hibernate SessionFactory .....");
                factory = buildSessionFactory();
                if (factory != null) {
                    // save Hibernate SessionFactory to HashMap
                    ServletContextListenerForVod.httpSessionHibernateFactoryMap.put(session, factory);
                }
                else
                {
                    // failed to create a new Hibernate SessionFactory
                    System.out.println("Failed to create a new Hibernate SessionFactory.");
                }
            }
        }

        System.out.println("After getStoredSessionFactory-> httpSessionHibernateFactoryMap.size() = "+ServletContextListenerForVod.httpSessionHibernateFactoryMap.size());

        return factory;
    }
}
