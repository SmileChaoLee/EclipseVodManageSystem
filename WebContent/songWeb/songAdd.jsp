<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    
    <script type="text/javascript" src="../jQuery/jquery-3.2.1.min.js"></script>
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

    <title>Add Songs</title>
</head>

<body>  
    <h1 style="margin: 0px 0px 0px 0px;">Add one song</h1>
    <form action="../songController/SongAddServlet" method="post"> 
        <!-- jsp:include page="songOneRecordForm.jsp"/ -->
        <%@ include file="songOneRecordForm.jsp" %>
    </form>
</body>
</html>