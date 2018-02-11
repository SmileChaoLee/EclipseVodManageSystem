package com.smile.controller;

import com.smile.util.*;
import com.smile.dataModel.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public SecurityServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("SecurityServlet init() !!");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
	
    @SuppressWarnings("rawtypes")	// for Hibernate Query
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		
    		/*
    		// use JDBC connection
        // if database connection does not exist then create a new one
        System.out.println("SecurityServlet.htm .....");
        Connection dbConn = JdbcMysql.getStoredConnection(request, true);
        if (dbConn == null) {
            System.out.println("SecurityServlet.htm -> dbConn is null. ");
            request.setAttribute("error", "Failed to access to database.");
            RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
            view.forward(request, response);
            return;
        }

        String name = request.getParameter("username");
        String pass = request.getParameter("password");

        if ( (name != null) && (pass != null) )
        {
            if ( (!name.isEmpty())) {
                name = name.trim();
                pass = pass.trim();
                try
                {
                    String sqlString = "select * from user where userName = ? and userPassword = ?";
                    PreparedStatement st = dbConn.prepareStatement(sqlString);
                    st.setString(1, name);
                    st.setString(2, pass);
                    ResultSet rs = st.executeQuery();

                    if (rs.next()) {
                        // succeeded to login
                        request.setAttribute("error", "");
                        RequestDispatcher view = request.getRequestDispatcher("/songMenu.jsp");   // go through the method that
                        view.forward(request, response);                                          // the calling program does
                        // response.sendRedirect("songMenu.jsp"); // go through GET method
                    }
                    else {
                        request.setAttribute("error", "Unknown user or wrong password, please try again.");
                        RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                        view.forward(request, response);
                    }
                }
                catch (SQLException ex) {
                    System.out.println(ex.toString());
                }
            }
            else {
                request.setAttribute("error", "User name can not be blank, please try again.");
                RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
            }
        } else {
            request.setAttribute("error", "user name and password must be inputed, please try again.");
            RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
            view.forward(request, response);
        }
        */
        //
        
        // use Hibernate to do data access            
        SessionFactory factory = HibernateUtils.getStoredSessionFactory(request, true);
        if (factory == null) {
            System.out.println("SecurityServlet.htm -> SessionFactory is null. ");
            // go to login page (index.jsp)
            RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
            view.forward(request, response);
            return;
        }
        
        String name = request.getParameter("username");
        String pass = request.getParameter("password");

        if ( (name != null) && (pass != null) ) {
            if ( (!name.isEmpty())) {
                name = name.trim();
                pass = pass.trim();
                try {
                		Session session = factory.openSession();
                		Query query = session.createQuery("FROM User WHERE userName = :uName AND userPassword = :uPass");
                		query.setParameter("uName", name);
                		query.setParameter("uPass", pass);
                		User user = (User)query.getSingleResult();
                		// String username = user.getUserName();
                		// String userpass = user.getUserPassword();
                		session.close();
            			// found this user, then logged in successfully
                    request.setAttribute("error", "");
                    RequestDispatcher view = request.getRequestDispatcher("/songMenu.jsp");   // go through the method that
                    view.forward(request, response);                                          // the calling program does
                    // response.sendRedirect("songMenu.jsp"); // go through GET method
                } catch (Exception ex) {
                		// if no entities in query ()not found) then throw exception  
                    ex.printStackTrace();
                    request.setAttribute("error", "Unknown user or wrong password, please try again.");
                    RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                    view.forward(request, response);
                }
            } else {
                request.setAttribute("error", "User name can not be blank, please try again.");
                RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
            }
        } else {
            request.setAttribute("error", "user name and password must be inputed, please try again.");
            RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
            view.forward(request, response);
        }
    }
}
