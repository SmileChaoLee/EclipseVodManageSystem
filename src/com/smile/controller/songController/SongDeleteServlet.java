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
@WebServlet(name = "SongDeleteServlet", urlPatterns = {"/songController/SongDeleteServlet"})
public class SongDeleteServlet extends HttpServlet {

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
        
        System.out.println("SongDeleteServlet->doGet() ....");
        
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
        
        String song_no = request.getParameter("song_no");
        Song songTmp = songTable.getOneRecordSongbySongno(song_no,songAttr.getByOrder());
        
        request.setAttribute("song", songTmp);
        
        songAttr.setOrgSong_no(song_no);
        HttpServletUtil.setSongAttributes(request, songAttr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songDelete.jsp");
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
                
        System.out.println("SongDeleteServlet->doPost() ....");
        
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
            int result = songTable.deleteOneRecordSongTable(songAttr.getOrgSong_no()) ;
            if (result == 0) {
                // succeeded to delete
                System.out.println("Succeeded to delete.");
                String qCon = songTable.findQueryConditionOnSongNo(songAttr.getQueryCondition(),songAttr.getOrgSong_no());
                pageNo = songTable.recalculatePageNo(qCon,"song.song_no",1,songAttr.getByOrder(),songAttr.getQueryCondition());
            }
            else
            {
                System.out.println("Failed to delete.");
            }
        }
        //else if (submitButton.equalsIgnoreCase("BACK")) {}
        
        request.setAttribute("songs", songTable.getOnePageOfSongTable(pageNo,songAttr.getQueryCondition(),songAttr.getByOrder()));
        
        songAttr.setPageNo(pageNo);
        HttpServletUtil.setSongAttributes(request, songAttr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songList.jsp");
        view.forward(request, response);
    }

}
