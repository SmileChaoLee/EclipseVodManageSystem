<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
 
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript">
        function toSubmitGeneratedForm(url, langid) {
            // If there is no form in body, then use this submit function
            var $form = $('<form action="'+url+'" method="get"></form>');
            if (langid !== undefined) {
                $form.append('<input type="hidden" name="language_id" value="'+langid+'" />');
            }
            $form.append('<input type="hidden" name="languageAttributesJson" value="${languageAttributesJson}" />');
            $form.appendTo('body').submit();
        }

        function toSubmitBodyForm(form, url, langid) {
            // If there is form in body, then use this submit function
            if (url !== undefined) {
                $(form).attr("action", url);
                if (langid !== undefined) {
                    $(form).append('<input type="hidden" name="language_id" value="'+langid+'" />');
                }
                $(form).append('<input type="hidden" name="languageAttributesJson" value="${languageAttributesJson}" />');
                $(form).submit();
            }
        }

        // use jQuery
        function toSubmit(url0, langid) {
            var url = "<%=request.getContextPath()%>/languageController/" + url0;
            var form = "#bodyForm";
            if ( $(form).length ) {
                // if form with id="bodyForm" exist. jQuery the length is greater than 0.
                toSubmitBodyForm(form, url, langid);
            } else {
                // alert("bodyForm does not exist");
                toSubmitGeneratedForm(url, langid);
            }
        }

        // use JavaScript
        function toSubmitJavaScript(url0, langid) {
            var url = "<%=request.getContextPath()%>/languageController/" + url0;
            var form = document.getElementById("bodyForm");
            if (form !== null ) {
                // if form with id="bodyForm" exist.
                toSubmitGeneratedForm(url, langid);
            } else {
                toSubmitBodyForm(url, langid);
            }
        }
        // Using jQuery to load a page.
        $(function() {
            // load logout page
            $('#logoutJsp').load("<%=request.getContextPath()%>/logout.jsp");
        });		
    </script>

    <style>
        body {
            background-color:#d0e4fe;
        }
        h1 {
            color: green;
            font-size: 20px;
            text-align:center;
        }
        p {
            font-family:"New Roman";
            font-size:12px;
            text-align:left;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid black;
        }
        th {
            font-family:"New Roman";
            font-size:12px;
            text-align:center;
            border: 1px solid black;
        }
        td {
            font-family:"New Roman";
            font-size:12px;
            text-align:left;
            border: 1px solid black;
        }
        button {
            font-family: "New Roman";
            color:DarkRed;
            background-color:lightgrey;
            height:28px; font-size:20px;
            text-align:center;
        }
        button.table {
            font-family: "New Roman";
            color:yellow;
            background-color:green;
            height:18px;font-size:12px;
            text-align:center;
        }
    </style>

    <title>Languages Management</title>
</head>

<body>    
 
<div id="logoutJsp" style="text-align:right;"></div>
<h1 style="margin: 0px 0px 0px 0px;">Languages Management</h1>

<form id="bodyForm" action="" method="get" >			
    <table style="margin:auto" border=1>
        <thead>
            <tr>
                <th>Language_No</th>            
                <th>Language_Na</th>
                <th>Language_En</th>
                <th/>
                <th/>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${languages}" var="language">
                <tr>
                    <td>${language.langNo}</td>                
                    <td>${language.langNa}</td>
                    <td>${language.langEn}</td>
                    
                    <td>
                        <button class="table" onclick="toSubmit('LanguageUpdateServlet','${language.id}')">UPDATE</button>
                    </td>
                    <td>
                        <button class="table" onclick="toSubmit('LanguageDeleteServlet','${language.id}')">DELETE</button>
                    </td>    
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p style="text-align:center; font-family:'New Roman'; font-size:16px; margin:12px 0px 0px 0px">
        <button onclick="toSubmit('LanguageAddServlet')">Add Languages</button>&nbsp;
        <button onclick="toSubmit('LanguageFindServlet')">Find Languages</button>&nbsp;  
        <button onclick="toSubmit('LanguageFirstPageServlet')">First Page</button>&nbsp;
        <button onclick="toSubmit('LanguageLastPageServlet')">Last Page</button>&nbsp; 
        <button onclick="toSubmit('LanguagePreviousPageServlet')">Previous Page</button>&nbsp;
        <button onclick="toSubmit('LanguageNextPageServlet')">Next Page</button>&nbsp;
        <button style="color:red;" onclick="toSubmit('LanguageQuitServlet')">QUIT</button>
    </p>
</form>

</body>
</html>