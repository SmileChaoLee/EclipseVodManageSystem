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

@WebServlet(name = "SongFindServlet", urlPatterns = {"/songController/SongFindServlet"})
public class SongFindServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public SongFindServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        System.out.println("SongFindServlet->doGet() ....");
     
        RequestAttributesForSong songAttr = HttpServletUtil.getSongAttributes(request);
        
        Song songTmp = new Song();
        request.setAttribute("song",songTmp);
        
        HttpServletUtil.setSongAttributes(request, songAttr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songWeb/songFind.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // response.getWriter().append("Served at: ").append(request.getContextPath());
        
        System.out.println("SongFindServlet->doPost() ....");
        
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
            String sType = request.getParameter("search_type");
            System.out.println("search_type= "+sType);
            if (sType != null) {
                if (sType.equalsIgnoreCase("song_no")) {
                    String song_no = request.getParameter("song_no");
                    String qCon = songTable.findQueryConditionOnSongNo(songAttr.getQueryCondition(),song_no);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.songNoOrder,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("vod_no")) {
                    String vod_no = request.getParameter("vod_no");
                    String qCon = songTable.findQueryConditionOnVodNo(songAttr.getQueryCondition(),vod_no);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.vodNoOrder,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("song_na")) {
                    String song_na  = request.getParameter("song_na");
                    String qCon = songTable.findQueryConditionOnSongNa(songAttr.getQueryCondition(),song_na);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.songNaOrder,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("lang_songname")) {
                    String lang_no  = request.getParameter("lang_no");
                    String song_na  = request.getParameter("song_na");
                    String qCon = songTable.findQueryConditionOnLangSongNa(songAttr.getQueryCondition(),lang_no,song_na);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.langSongNaOrder,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("lang_sword_songname")) {
                    String lang_no = request.getParameter("lang_no");
                    String sword   = request.getParameter("sword");
                    String song_na = request.getParameter("song_na");
                    String qCon = songTable.findQueryConditionOnLangSwordSongNa(songAttr.getQueryCondition(),lang_no,sword,song_na);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.langSwordSongNaOrder,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("lang_sword_songno")) {
                    System.out.println(sType);
                    String lang_no  = request.getParameter("lang_no");
                    String sword     = request.getParameter("sword");
                    String song_no   = request.getParameter("song_no");
                    String qCon = songTable.findQueryConditionOnLangSwordSongNo(songAttr.getQueryCondition(),lang_no,sword,song_no);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.langSwordSongNoOrder,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("singer1_name")) {
                    String sing_na1 = request.getParameter("sing_na1");
                    String qCon = songTable.findQueryConditionOnSingNa1(songAttr.getQueryCondition(),sing_na1);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.singerOrder1,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                } else if (sType.equalsIgnoreCase("singer2_name")) {
                    String sing_na2 = request.getParameter("sing_na2");
                    String qCon = songTable.findQueryConditionOnSingNa2(songAttr.getQueryCondition(),sing_na2);
                    pageNo = songTable.recalculatePageNo(qCon,songTable.singerOrder2,0,songAttr.getByOrder(),songAttr.getQueryCondition());
                }
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
