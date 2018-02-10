<%-- 
    Document   : song2Delete
    Created on : 26-Aug-2017, 1:13:31 PM
    Author     : chaolee
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    
    <script type="text/javascript" src="../jQuery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript">
        // jQuery function to set focus on first input item
        $(function() {
            $('#seleYN').val("${song.sele_tf}");
            $('#chorYN').val("${song.chor}");
            $('#vodYN').val("${song.vod_yn}");
            $('form input[type="text"]').prop("readonly",true);
            $('form input[type="date"]').prop("readonly",true);
            $('form select').prop("disabled",true);
            $('form input:first').focus();
        });
    </script>

    <link rel="stylesheet" type="text/css" href="../songWeb/songOneRecordStyle.css">

    <title>Delete song</title>
</head>

<body>  
    <h1>Delete one song</h1>
    <form action="../songController/SongDeleteServlet" method="post"> 
        <!-- jsp:include page="songOneRecordForm.jsp"/ -->
        <%@ include file="songOneRecordForm.jsp" %>
    </form>
</body>
</html>
