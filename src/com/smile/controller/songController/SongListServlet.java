package com.smile.controller.songController;

import com.smile.dao.*;
import com.smile.model.objectmodel.*;
import com.smile.util.*;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "SongListServlet", urlPatterns = {"/songController/SongListServlet"})
public class SongListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.getWriter().append("Served at: ").append(request.getContextPath());

        System.out.println("SongListServlet->doGet() ....");

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
