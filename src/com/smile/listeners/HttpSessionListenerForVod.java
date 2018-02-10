package com.smile.listeners;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

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

            ServletContextListenerForVod.sessionMap.put(session,null);
            System.out.println("HttpSessionListenerForVod->HashMap size() up to " + ServletContextListenerForVod.sessionMap.size());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
            System.out.println("HttpSessionListenerForVod->HashMap size() before remove " + ServletContextListenerForVod.sessionMap.size());
            try
            {
                HttpSession session = event.getSession();
                if (session != null)
                {
                    Connection JdbcConnection = ServletContextListenerForVod.sessionMap.get(session);
                    if (JdbcConnection != null)
                    {
                            System.out.println("HttpSessionListenerForVod->JdbcConnection is not null!!");
                            JdbcConnection.close();
                    }
                    ServletContextListenerForVod.sessionMap.remove(session);	
                    System.out.println("HttpSessionListenerForVod->HashMap size() down to " + ServletContextListenerForVod.sessionMap.size());
                }
                else
                {
                    System.out.println("HttpSessionListenerForVod->session is null.");
                }
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
	}
}
