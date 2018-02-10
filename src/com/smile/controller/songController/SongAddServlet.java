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
@WebServlet(name = "SongAddServlet", urlPatterns = {"/songController/SongAddServlet"})
public class SongAddServlet extends HttpServlet {
	
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        System.out.println("SongAddServlet->doGet() ....");
        
        RequestAttributesForSong songAttr = HttpServletUtil.getSongAttributes(request);
        
        Song songTmp = new Song(); 
        request.setAttribute("song", songTmp);
        
        HttpServletUtil.setSongAttributes(request, songAttr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songAdd.jsp");
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
        
        System.out.println("SongAddServlet->doPost() ....");
        
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
        
        int pageNo = songAttr.getPageNo();
        String submitButton = request.getParameter("submitButton");
        if (submitButton.equalsIgnoreCase("SUBMIT")) {
            Song song = songTable.getDataFromJspView(request);
            int result = -1;
            if (song != null) {
                if (song.getSongNo().isEmpty()) {
                    request.setAttribute("error", "Song NO. can not be empty !!");
                } else {
                    result = songTable.insertOneRecordSongTable(song);
                    if (result == 0) {
                        // successfully inserted
                        request.setAttribute("error", "");
                    } else {
                        // result != 1, failed to inserted
                        if (result == JdbcMysql.MYSQL_DUPLICATE_PK) {
                                request.setAttribute("error","Song No. can not be duplicated !!");
                        } else {
                                request.setAttribute("error","May be some problem with database !!");
                        }
                    }
                }
            } else {
                request.setAttribute("error","Song No. can not be NULL !!");
            }
            if (result != 0) {
                // unsuccessfully inserted, keep the input information on screen
                request.setAttribute("song", song);
                // then modify it
            } else {
                // successfully inserted
                String qCon = songTable.findQueryConditionOnSongNo(songAttr.getQueryCondition(),song.getSongNo());
                pageNo = songTable.recalculatePageNo(qCon,"song.song_no",0,songAttr.getByOrder(),songAttr.getQueryCondition());
                Song songTmp = new Song();
                request.setAttribute("song", songTmp );
                // then add another one
            }
            songAttr.setPageNo(pageNo);
            HttpServletUtil.setSongAttributes(request, songAttr);
            
            RequestDispatcher view = request.getRequestDispatcher("/songWeb/songAdd.jsp");
            view.forward(request,response);
            return;
        }
        //else if (submitButton.equalsIgnoreCase("BACK")) {}
        
        request.setAttribute("songs", songTable.getOnePageOfSongTable(pageNo,songAttr.getQueryCondition(),songAttr.getByOrder()));
        
        songAttr.setPageNo(pageNo);
        HttpServletUtil.setSongAttributes(request, songAttr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songList.jsp");
        view.forward(request, response);
    }
}
