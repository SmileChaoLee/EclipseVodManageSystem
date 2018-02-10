<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

    <link rel="stylesheet" type="text/css" href="../singerWeb/singerOneRecordStyle.css" >

    <title>Add Singers</title>
    
</head>

<body>
    <h1 style="margin: 0px 0px 0px 0px;">Add one singer</h1>
    <form action="../singerController/SingerAddServlet" method="post"> 
        <%@ include file="singerOneRecordForm.jsp" %>
    </form>
</body>

</html>