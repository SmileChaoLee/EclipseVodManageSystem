package com.smile.controller;

import com.smile.listeners.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LogoutServlet", urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // TODO Auto-generated method stub
            System.out.println("LogoutServlet->httpSessionConnectionMap.size() before logout = " + ServletContextListenerForVod.httpSessionConnectionMap.size());
            System.out.println("LogoutServlet->httpSessionHibernateFactoryMap.size() before logout = " + ServletContextListenerForVod.httpSessionHibernateFactoryMap.size());
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                System.out.println("LogoutServlet->httpSessionConnectionMap.size() after logout = " + ServletContextListenerForVod.httpSessionConnectionMap.size());
                System.out.println("LogoutServlet->httpSessionHibernateFactoryMap.size() after logout = " + ServletContextListenerForVod.httpSessionHibernateFactoryMap.size());
                RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
            }
            else {
                System.out.println("session is null.");
            }
    }
}
