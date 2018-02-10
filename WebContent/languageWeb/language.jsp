
<%@ page import="com.smile.util.*" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%
            String languageAttributesJson = HttpServletUtil.languageAttributesJson();
            // System.out.println("language.jsp ---> languageAttributesJson = " + languageAttributesJson);
            // request.setAttribute("languageAttributesJson", languageAttributesJson);
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

    <form action="<%=request.getContextPath()%>/languageController/LanguageListServlet" method="get">
        <!-- 
			value has to use single quote (') because the real value is JSON string that include double quote (")
         -->
        <!-- input type='hidden' name='languageAttributesJson' value='${languageAttributesJson}' / -->
        <input type='hidden' name='languageAttributesJson' value='<%=languageAttributesJson%>' />
    </form>

</body>
</html>