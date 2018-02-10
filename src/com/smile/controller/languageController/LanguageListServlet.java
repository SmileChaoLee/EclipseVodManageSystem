package com.smile.controller.languageController;

import com.smile.dao.*;
import com.smile.util.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

@WebServlet(name = "LanguageListServlet", urlPatterns = {"/languageController/LanguageListServlet"})
public class LanguageListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.getWriter().append("Served at: ").append(request.getContextPath());

        System.out.println("LanguageListServlet->doGet() ....");
        
        // get attribute from request
        	String jsonString = request.getParameter("languageAttributesJson");
        	// System.out.println("LanguageListServlet ---> jsongString = " + jsonString);
        	// set attribute to keep the attribute
        request.setAttribute("languageAttributesJson", jsonString);
        	
        	if ( (jsonString == null) || (jsonString.isEmpty()) ) {
                PrintWriter prtw = response.getWriter();
                prtw.println("<!DOCTYPE html>");
                prtw.println("<html>");
                prtw.println("<body>");
                prtw.println("<p><h1>JSONG string is empty !!</h1></p>");
                prtw.println("</body>");
                prtw.println("</html>");
                prtw.close();
                
                return;
        	}
        	
        	/*
        PrintWriter prtw = response.getWriter();
        prtw.println("<!DOCTYPE html>");
        prtw.println("<html>");
        prtw.println("<body>");
        prtw.println("<p><h1> jsonString = " + jsonString + "</h1></p>");
        prtw.println("</body>");
        prtw.println("</html>");
        prtw.close();
        
        return;
        */

        try {
        		// do not create a new database connection if connection does not exist for this session
            Connection dbConn = JdbcMysql.getStoredConnection(request,false);
            if (dbConn == null) {
                // go to login page (index.jsp)
                RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
                return;
            }
            
    			JSONParser jsonParser = new JSONParser();
    			JSONObject json = (JSONObject)jsonParser.parse(jsonString);
    			long pageNo = (Long)json.get("pageNo");
            	// System.out.println("LanguageListServlet ---> pageNo = " + pageNo);
    			
            LanguageTable languageTable = new LanguageTable(dbConn);
            languageTable.setCurrentPageNo((int)pageNo);

            // set attribute for request
            request.setAttribute("languages", languageTable.getCurrentPage());

            RequestDispatcher view = request.getRequestDispatcher("/languageWeb/languageList.jsp");
            view.forward(request, response);
            
        } catch (Exception ex) {
        		ex.printStackTrace();
            PrintWriter prtw = response.getWriter();
            prtw.println("<!DOCTYPE html>");
            prtw.println("<html>");
            prtw.println("<body>");
            prtw.println("<p><h1> Exception happened becuase of some reasons </h1></p>");
            prtw.println("</body>");
            prtw.println("</html>");
            prtw.close();
        }
        
        return;
    }

}
