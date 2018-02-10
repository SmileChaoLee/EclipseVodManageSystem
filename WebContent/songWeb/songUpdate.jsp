<%-- 
    Document   : SongUpdate
    Created on : 26-Aug-2017, 1:16:36 PM
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
            // $('form input[type="text"]').prop("readonly",false);
            // $('form input[type="date"]').prop("readonly",false);
            // $('form select').prop("disabled",false);
            $('form input:first').focus();
        });
    </script>

    <link rel="stylesheet" type="text/css" href="../songWeb/songOneRecordStyle.css">

    <title>Update song</title>
</head>

<body>  
    <h1>Update one song</h1>
    <form action="../songController/SongUpdateServlet" method="post"> 
        <!-- jsp:include page="songOneRecordForm.jsp"/ -->
        <%@ include file="songOneRecordForm.jsp" %>
    </form>
</body>
</html>