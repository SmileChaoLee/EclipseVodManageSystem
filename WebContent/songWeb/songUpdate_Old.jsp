<%@ page import="com.smile.dataModel.*" %>
<%@ page import="com.smile.model.objectmodel.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

	<script type="text/javascript" src="jQuery/jquery-1.11.3.min.js"></script>
		
	<%
	// <c:if test="${accessMethod=='DELETE'}">
	// <c:out value="${'readonly'}"/>
	// </c:if>
	
        String inputC = new String("");
        String selectC = new String("");
        String formTitle = new String("");
        String formSubtitle = new String("");
		
	    String select_YES = "";
	    String select_NO  = "";
	    String chor_YES   = "";
	    String chor_NO    = "";
	    String vod_YES    = "";
	    String vod_NO     = "";
	   
	    RequestAttributesForSong songAttr = (RequestAttributesForSong)request.getAttribute("songAttributes");
            String accMethod = songAttr.getAccessMethod();
            if (accMethod.equalsIgnoreCase("DELETE")) {
                    inputC = "disabled";
                    selectC = "disabled";
                    formTitle = "Delete one Record from Table song";
                    formSubtitle = "";
            } else if (accMethod.equalsIgnoreCase("ADD")) {
                    formTitle = "Add one Record into Table song";
                    formSubtitle = "Please input a song's data by fill out the following form";
            } else if (accMethod.equalsIgnoreCase("UPDATE")) {
                    formTitle = "Update one Record from Table song";
                    formSubtitle = "Please input a song's data by fill out the following form";
            } else {
                    formTitle = "Unknow Access Method";
            }
	
	   	Song ss2 = (Song)request.getAttribute("song");
	   	
	   	if (ss2==null)
	   	{
	   		ss2 = new Song();
	    }
	    
	    if (ss2.getSeleTf()!=null)
	    {
	    	if (ss2.getSeleTf() == 'T')
	    	{
	    		select_YES = "selected";
	    	}
	    	else
	    	{
	    		select_NO = "selected";
	    	}
	    }
	    
	    if (ss2.getChor()!=null)
	    {
	    	if (ss2.getChor().equalsIgnoreCase("Y"))
	    	{
	    		chor_YES = "selected";
	    	}
	    	else
	    	{
	    		chor_NO = "selected";
	    	}
	    }
	    
	    if (ss2.getVodYn()!=null)
	    {
	    	if (!ss2.getVodYn().equalsIgnoreCase("N"))
	    	{
	    		vod_YES = "selected";
	    	}
	    	else
	    	{
	    		vod_NO = "selected";
	    	}
	    }
	
	%>  
	
	<script type="text/javascript">
		// jQuery function to set focus on first input item
		$(function(){
                    $("form input:first").focus();
		});
	</script>
	
	<style>
            body {
                background-color:#d0e4fe;
            }
            h1 {
                color:green;
                text-align:center;
            }
            p {
                font-family:"New Roman";
                font-size:14px;
                text-align:left;
            }
            span.errorMsg {
                font-family:"New Roman";
                font-size:20px;width:400px;
                text-align:left;
                color:red;
            }
            input:enabled {
                font-family:"New Roman";
                font-size:14px;width:400px;
                text-align:left;
                color:black;
                background:white;
            }
            input:disabled {
                font-family:"New Roman";
                font-size:14px;width:400px;
                text-align:left;
                color:blue;
                background-color:#d0e4fe;
            }
            select:enabled {
                font-family:"New Roman";
                font-size:14px;width:400px;
                text-align:left;
                color:black;
                background:white;
            }
            select:disabled {
                font-family:"New Roman";
                font-size:14px;width:400px;
                text-align:left;
                color:blue;
                background-color:#d0e4fe;
            }
            input.submit {
                font-family:"New Roman";
                font-size:30px;width:200px;
                text-align:left;
                color:yellow;
                background:green;
            }
            table {
                font-family:"New Roman";
                margin:auto;
            }
            td {
                text-align:left;
            }
	</style>

	<title>Add a new song</title>
</head>

<body>  
	<h1> <%=formTitle %></h1>
	<h1> <%=formSubtitle %></h1>

	<form action="SongServlet.html" method="post" name="frmUpdateSong">
		<table border=1>
		<tr><td>
				<span class="errorMsg" style="color:blue">Error Message</span>
			</td>
			<td>
				<span class="errorMsg" style="color:red">${error}</span>
			</td>
		</tr>
		
		<tr><td>Song No.</td>
			<td>
			<input <%=inputC%> type="text" maxlength="6"  name="song_no" value="${song.song_no}" > 
						
			<input type="hidden" name="accessMethod" 	value="${accessMethod}" readonly >
			<input type="hidden" name="orgSong_no"   	value="${orgSong_no}" readonly >
			<input type="hidden" name="queryCondition"	value="${requeryCondition}" readonly >
			<input type="hidden" name="byOrder"   		value="${byOrder}" readonly >
			<input type="hidden" name="pageNo"   		value="${pageNo}" readonly >          
        	</td>
        </tr>
        
        <tr><td>Song Title</td>
        	<td>
        	<input <%=inputC%> type="text" maxlength="36" name="song_na" value="${song.song_na}" >
        	</td>
        </tr>
        
        <tr><td>Language NO.</td>
        	<td>
        	<input <%=inputC%> type="text" style="width:100px;" maxlength="1"  name="lang_no" value="${song.lang_no}" >
        	<input type="text" style="width:300px;" name="lang_na" value="${song.lang_na}" readonly>
			</td>
		</tr>
		
		<tr><td>Num_Of_word</td>
			<td>
			<input <%=inputC%> type="text" maxlength="2"  name="s_num_word" value="${song.s_num_word}" >
			</td>
		</tr>
		
        <tr><td>Num_fw</td>
        	<td>
        	<input <%=inputC%> type="text" maxlength="2"  name="num_fw"     value="${song.num_fw}"     >
        	</td>
        </tr>
        <tr><td>Num_pw</td>
        	<td>
        	<input <%=inputC%> type="text" maxlength="1"  name="num_pw"     value="${song.num_pw}"     >
        	</td>
        </tr>
        <tr><td>Singer1 No.</td>
        	<td>
        	<input <%=inputC%> type="text" style="width:100px;" maxlength="5"  name="sing_no1" value="${song.sing_no1}"   >
        	<input type="text" style="width:300px;" name="sing_na1" value="${song.sing_na1}" readonly>
        	</td>
        </tr>
        <tr><td>Singer2 No.</td>
        	<td>
        	<input <%=inputC%> type="text" style="width:100px;" maxlength="5"  name="sing_no2" value="${song.sing_no2}"   >
        	<input type="text" style="width:300px;" name="sing_na2" value="${song.sing_na2}" readonly>
        	</td>
        </tr>
        <tr><td>Select_tf</td>
        	<td>
        	<select <%=selectC%> name="sele_tf">
        		<option value="N" <%=select_NO%>>No</option>
        		<option value="Y" <%=select_YES%>>Yes</option>
        	</select>
        	</td>
        </tr>
        <tr><td>Chor</td>
        	<td>
        	<select <%=selectC%> name="chor">
        		<option value="N" <%=chor_NO%>>No</option>
        		<option value="Y" <%=chor_YES%>>Yes</option>
        	</select>
        	</td>
        </tr>
        <tr><td>Music_track</td>
        	<td>
        	<input <%=inputC%> type="text" maxlength="2"  name="n_mpeg"     value="${song.n_mpeg}"     >                    
        	</td>
        </tr>
        <tr><td>Vocal_Track</td>
        	<td>
       		<input <%=inputC%> type="text" maxlength="2"  name="m_mpeg"     value="${song.m_mpeg}"     >
        	</td>
        </tr>
        <tr><td>Vod_yn</td>
        	<td>
        	<select <%=selectC%> name="vod_yn">
        		<option value="Y" <%=vod_YES%>>Yes</option>
        		<option value="N" <%=vod_NO%>>No</option>
        	</select>
        	</td>
        </tr>
        <tr><td>Vod_no</td>
        	<td>
        	<input <%=inputC%> type="text" maxlength="6"  name="vod_no"     value="${song.vod_no}"     >
        	</td>
        </tr>
        <tr><td>PathName</td>
        	<td>
        	<input <%=inputC%> type="text" maxlength="6"  name="pathname"   value="${song.pathname}"   >
        	</td>
        </tr>
        <tr><td>In_Date</td>
        	<td>
        	<input <%=inputC%> type="date" maxlength="10" name="in_date"    value="${song.in_date}"    >
        	</td>
        </tr>
		
		<tr>
		<td></td>
		<td style="text-align:center">
        	<input class="submit" type="submit" name="submitButton" value="Submit">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<input class="submit" type="submit" name="submitButton" value="Back">
        </td>
        </tr>
        
		</table>
	</form>
</body>
</html>