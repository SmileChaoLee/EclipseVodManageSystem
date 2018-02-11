package com.smile.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.hibernate.SessionFactory;

import java.sql.Connection;

import javax.servlet.http.HttpSession;

public class HttpSessionListenerForVod implements HttpSessionListener  {
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
        HttpSession session = event.getSession();

        // Set timeout of inactive to 10 minutes.
        // means sessionDestroyed will be called if it has been inactive more than 10 minutes
        session.setMaxInactiveInterval(20*60);	// 20 minutes
        //

        ServletContextListenerForVod.httpSessionConnectionMap.put(session, null);
        System.out.println("HttpSessionListener --> httpSessionConnectionMap.size() up to " + ServletContextListenerForVod.httpSessionConnectionMap.size());
        
        ServletContextListenerForVod.httpSessionHibernateFactoryMap.put(session, null);
        System.out.println("HttpSessionListener --> httpSessionHibernateFactoryMap.size() up to " + ServletContextListenerForVod.httpSessionHibernateFactoryMap.size());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		//
        System.out.println("HttpSessionListener --> httpSessionConnectionMap.size() before remove " + ServletContextListenerForVod.httpSessionConnectionMap.size());
        try
        {
            HttpSession session = event.getSession();
            if (session != null) {
                Connection JdbcConnection = ServletContextListenerForVod.httpSessionConnectionMap.get(session);
                if (JdbcConnection != null) {
                        System.out.println("HttpSessionListener --> JdbcConnection is not null!!");
                        JdbcConnection.close();
                }
                ServletContextListenerForVod.httpSessionConnectionMap.remove(session);	
                System.out.println("HttpSessionListener --> httpSessionConnectionMap.size() down to " + ServletContextListenerForVod.httpSessionConnectionMap.size());
            } else {
                System.out.println("HttpSessionListener --> HttpSession is null.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //
        
        //
        System.out.println("HttpSessionListener --> httpSessionHibernateFactoryMap.size() before remove " + ServletContextListenerForVod.httpSessionHibernateFactoryMap.size());
        try
        {
            HttpSession session = event.getSession();
            if (session != null) {
                SessionFactory factory = ServletContextListenerForVod.httpSessionHibernateFactoryMap.get(session);
                if (factory != null) {
                    System.out.println("HttpSessionListener --> factory is not null!!");
                    factory.close();
                }
                ServletContextListenerForVod.httpSessionHibernateFactoryMap.remove(session);	
                System.out.println("HttpSessionListener --> httpSessionHibernateFactoryMap.size() down to " + ServletContextListenerForVod.httpSessionHibernateFactoryMap.size());
            } else {
                System.out.println("HttpSessionListener --> HttpSession is null.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //
	}
}
