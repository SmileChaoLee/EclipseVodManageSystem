/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smile.util;

import com.smile.model.objectmodel.RequestAttributesForSong;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

/**
 *
 * @author chaolee
 */
public class HttpServletUtil {
    
    public static RequestAttributesForSong getSongAttributes(HttpServletRequest request) {

        String songAttributesObject = request.getParameter("songAttributesObject");
        RequestAttributesForSong songAttr = (RequestAttributesForSong)request.getSession().getAttribute(songAttributesObject);
        request.getSession().removeAttribute(songAttributesObject);
        
        return songAttr;
    }
    
    public static void setSongAttributes(HttpServletRequest request, RequestAttributesForSong songAttr) {
        
        String songAttributesObject = UUID.randomUUID().toString();	// make it unique
        request.getSession().setAttribute(songAttributesObject, songAttr);
        request.setAttribute("songAttributesObject", songAttributesObject);
    }
    
    @SuppressWarnings("unchecked")
	public static String languageAttributesJson() {
		
		String actFunction = "LIST";
		int orgLanguageId = 0;
		String queryCondition = "";
		String byOrder = "language.lang_no";
		Long pageNo = new Long(1);
		String accessMethod = "";
		
		String result = "{}";
		try {
			JSONObject json = new JSONObject();
			json.put("actFunction", actFunction);
			json.put("orgLanguageId", orgLanguageId);
			json.put("queryCondition", queryCondition);
			json.put("byOrder", byOrder);
			json.put("pageNo", pageNo);
			json.put("accessMethod", accessMethod);	
			
			/*
			 * this is another way to have JSON string
			StringWriter out = new StringWriter();
			json.writeJSONString(out);
			if (out != null ) {
				result = out.toString();
			}
			*/
			
			// using this is enough
			result = json.toJSONString();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
    }
    
    @SuppressWarnings("unchecked")
	public static String songAttributesJson() {
		
		String actFunction = "LIST";
		String orgSongNo = "";
		String queryCondition = "";
		String byOrder = "song.song_no";
		Long pageNo = new Long(1);
		String accessMethod = "";
		
		String result = "{}";
		try {
			JSONObject json = new JSONObject();
			json.put("actFunction", actFunction);
			json.put("orgSongNo", orgSongNo);
			json.put("queryCondition", queryCondition);
			json.put("byOrder", byOrder);
			json.put("pageNo", pageNo);
			json.put("accessMethod", accessMethod);	
			
			/*
			 * this is another way to have JSON string
			StringWriter out = new StringWriter();
			json.writeJSONString(out);
			if (out != null ) {
				result = out.toString();
			}
			*/
			
			// using this is enough
			result = json.toJSONString();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return result;
    }
}
