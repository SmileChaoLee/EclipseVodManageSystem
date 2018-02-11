package com.smile.listeners;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;

import com.mysql.jdbc.Driver;
import com.smile.util.HibernateUtils;

/**
 * Application Lifecycle Listener implementation class ServletContextListenerForVod
 *
 */
public class ServletContextListenerForVod implements ServletContextListener {

    public static HashMap<HttpSession,Connection> httpSessionConnectionMap = null;
    public static HashMap<HttpSession,SessionFactory> httpSessionHibernateFactoryMap = null;
    
    public ServletContextListenerForVod() {
    		httpSessionConnectionMap = new HashMap<HttpSession,Connection>();
    		httpSessionHibernateFactoryMap = new HashMap<HttpSession,SessionFactory>();
        System.out.println("ServletContextListener constructed.");
    }

    public void contextDestroyed(ServletContextEvent arg0)  {
    	
    		//
    		System.out.println("Size of httpSessionConnectionMap= "+httpSessionConnectionMap.size());
        Iterator<HttpSession> itrHttp = httpSessionConnectionMap.keySet().iterator();
        while (itrHttp.hasNext()) {
            HttpSession session = itrHttp.next();
            try
            {
                Connection JdbcConnection = httpSessionConnectionMap.get(session);
                if (JdbcConnection != null)
                {
                        System.out.println("JdbcConnection is not null!!");
                        JdbcConnection.close();
                }
                // session has been invalidated when web container is being shutdown
                // before contextDestroued() being executed
                // session.invalidate();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        httpSessionConnectionMap.clear();

        Enumeration<java.sql.Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = (Driver)drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //
        
        //
		System.out.println("Size of httpSessionHibernateFactoryMap= "+httpSessionHibernateFactoryMap.size());
        Iterator<HttpSession> itrFactory = httpSessionHibernateFactoryMap.keySet().iterator();
        while (itrFactory.hasNext()) {
            HttpSession session = itrFactory.next();
            try
            {
                SessionFactory factory = httpSessionHibernateFactoryMap.get(session);
                if (factory != null)
                {
                        System.out.println("factory is not null!!");
                        factory.close();
                }
                // session has been invalidated when web container is being shutdown
                // before contextDestroued() being executed
                // session.invalidate();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        httpSessionHibernateFactoryMap.clear();
        //
        
        // HibernateUtils.shutdownSessionFactory();	// close Hibernate's SessionFactory

        System.out.println("Database KtvSystemDB disconnected !!");
    		System.out.println("ServletContextListener destroyed.");
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
    		System.out.println("ServletContextListener initialized.");
    }
	
}
