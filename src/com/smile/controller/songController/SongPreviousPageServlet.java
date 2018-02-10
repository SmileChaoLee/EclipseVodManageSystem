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
@WebServlet(name = "SongPreviousPageServlet", urlPatterns = {"/songController/SongPreviousPageServlet"})
public class SongPreviousPageServlet extends HttpServlet {
    
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
        
        System.out.println("SongPreviousPageServlet->doGet() ....");
        
        RequestAttributesForSong songAttr = HttpServletUtil.getSongAttributes(request);
        
        Connection dbConn = JdbcMysql.getStoredConnection(request,false);
        if (dbConn == null) {
                // go to login page (index.jsp)
                RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
                return;
        }
        SongTable songTable = new SongTable(dbConn);
        //
        
        // get the previous page no
        int pageNo = songAttr.getPageNo() - 1; // 
        if (pageNo < 1) {
            pageNo = 1;
        }
        
        request.setAttribute("songs", songTable.getOnePageOfSongTable(pageNo,songAttr.getQueryCondition(),songAttr.getByOrder()));
        
        songAttr.setPageNo(pageNo);
        HttpServletUtil.setSongAttributes(request, songAttr);

        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songList.jsp");
        view.forward(request, response);
    }

}
