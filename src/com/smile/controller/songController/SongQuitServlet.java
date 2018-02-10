/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.controller.songController;

import com.smile.util.*;
import com.smile.dataModel.*;
import com.smile.model.objectmodel.*;

import java.io.IOException;
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
@WebServlet(name = "SongQuitServlet", urlPatterns = {"/songController/SongQuitServlet"})
public class SongQuitServlet extends HttpServlet {
    
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
        
        System.out.println("SongQuitServlet->doGet() ....");
        
        // get attributes
        RequestAttributesForSong songAttr = HttpServletUtil.getSongAttributes(request);
        
        // set attributes
        HttpServletUtil.setSongAttributes(request, songAttr);
        
        RequestDispatcher view = request.getRequestDispatcher("/songMenu.jsp");
        view.forward(request, response);
    }

}
