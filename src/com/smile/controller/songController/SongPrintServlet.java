/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.controller.songController;

import com.smile.dao.*;
import com.smile.dataModel.*;
import com.smile.util.*;
import com.smile.model.objectmodel.*;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author chaolee
 */
@WebServlet(name = "SongPrintServlet", urlPatterns = {"/songController/SongPrintServlet"})
public class SongPrintServlet extends HttpServlet {
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("SongPrintServlet->doGet() ....");
     
        RequestAttributesForSong songAttr = HttpServletUtil.getSongAttributes(request);
        
        Song songTmp = new Song();
        request.setAttribute("song",songTmp);
        
        HttpServletUtil.setSongAttributes(request, songAttr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songPrint.jsp");
        view.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*
        PrintWriter prtw = response.getWriter();
        prtw.println("<!DOCTYPE html>");
        prtw.println("<html>");
        prtw.println("<body>");
        prtw.println("<p><h1>SongPrintServlet (POST method) !!</h1></p>");
        prtw.println("</body>");
        prtw.println("</html>");
        prtw.close();
        */
        
        System.out.println("SongPrintServlet->doPost() ....");

        RequestAttributesForSong songAttr = HttpServletUtil.getSongAttributes(request);

        // do not create a new database connection if connection does not exist for this session
        Connection dbConn = JdbcMysql.getStoredConnection(request,false);
        if (dbConn == null) {
            // go to login page (index.jsp)
            RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
            view.forward(request, response);
            return;
        }
        SongTable songTable = new SongTable(dbConn);
        //

        request.setAttribute("songs", songTable.getOnePageOfSongTable(songAttr.getPageNo(),songAttr.getQueryCondition(),songAttr.getByOrder()));

        HttpServletUtil.setSongAttributes(request, songAttr);

        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songList.jsp");
        view.forward(request, response);
    }

}
