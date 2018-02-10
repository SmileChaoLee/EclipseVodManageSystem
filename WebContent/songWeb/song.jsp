<%@ page import="java.util.UUID" %>
<%@ page import="com.smile.model.*" %>
<%@ page import="com.smile.model.objectmodel.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%
            // initialize a object of RequestAttributesForSong
            RequestAttributesForSong songAttr = new RequestAttributesForSong();
            String songAttributesObject = UUID.randomUUID().toString();	// make it unique
            request.getSession().setAttribute(songAttributesObject, songAttr);
            // request.setAttribute("songAttributesObject", songAttributesObject);
        %>
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/jquery-3.2.1.min.js"></script>
        <script type="text/javascript">
            // Using jQuery.
            $(document).ready( function() {
                $('form').submit();
            });
        </script>
        <title>songs</title>
</head>

<body>

    <form action="<%=request.getContextPath()%>/songController/SongListServlet" method="get">
        <!-- input type="hidden" name="songAttributesObject" value="${songAttributesObject}" / -->
        <input type="hidden" name="songAttributesObject" value="<%=songAttributesObject%>" />
    </form>

</body>
</html>