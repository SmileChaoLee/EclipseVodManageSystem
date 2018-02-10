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
import java.io.PrintWriter;
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
@WebServlet(name = "SongUpdateServlet", urlPatterns = {"/songController/SongUpdateServlet"})
public class SongUpdateServlet extends HttpServlet {

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
        System.out.println("SongUpdateServlet->doGe"
                + "t() ....");
        
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
        
        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songUpdate.jsp");
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
        
        System.out.println("SongUpdateServlet->doPost() ....");
        
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
                    result = songTable.updateOneRecordSongTable(songAttr.getOrgSong_no(),song) ;
                    if (result == 0) {
                        // successfully updated
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
                // failed to update
                System.out.println("Failed to update.");
                request.setAttribute("song", song);
                // keep modifying it
                
                HttpServletUtil.setSongAttributes(request, songAttr);
                
                RequestDispatcher view = request.getRequestDispatcher("/songWeb/songUpdate.jsp");
                view.forward(request, response);
                
                return;
            } else {
                // succeeded to update
                System.out.println("Succeeded to Update.");
                String qCon = songTable.findQueryConditionOnSongNo(songAttr.getQueryCondition(),song.getSongNo());
                pageNo = songTable.recalculatePageNo(qCon,"song.song_no",0,songAttr.getByOrder(),songAttr.getQueryCondition());
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
