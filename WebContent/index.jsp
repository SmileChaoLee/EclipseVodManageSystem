<%@ page import="com.smile.util.*" %>
<%@ page import="java.sql.Connection" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%
        // java code in jsp file
        // do not create a new database connection if connection does not exist for this session
        System.out.println("index.jsp ....");
        String error = (String)request.getAttribute("error");
        if ( (error == null) || error == "")
        {
            Connection dbConn = JdbcMysql.getStoredConnection(request,false);
            if (dbConn != null) {
                    // database connection exists which meads it is on login status
                    // go to songMenu.jsp
                    RequestDispatcher view = request.getRequestDispatcher("/songMenu.jsp");
                    view.forward(request, response);
                    return;
            }
        }
    %>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/jquery-3.2.1.min.js"></script>
    <!--  the following script might not be necessary -->
    <script type="text/javascript">
        // Using jQuery.
        $(function() {
            // jQuery function to set focus on first input item
            $('form input:first').focus();

            $('form').each(function() {
                $(this).find('input').keypress(function(e) {
                    // Enter pressed?
                    if(e.which === 10 || e.which === 13) {
                        this.form.submit();
                        // toSubmit();	// another option
                    }
                });
            });
        });
        
        function toSubmit()
        {
            // document.getElementById("submitButton").submit();
            $('form').find('button').submit();
        }
    </script>
    <style type="text/css">
        body {
                background-color:#d0e4fe;
                font-size:16px;
        }
        h1 {
                color:green;
                text-align:center;
        }
        p {
                font-family:"New Roman";
                font-size:20px;
                text-align:center;
        }
        input[type=submit] {
            border: none;
                color:brown;background-color:lightgrey;
                height: 26px; font-size: 16px; font-weight:bold;
                text-align:center;
        }
        button {
                color:brown;background-color:lightgrey;
                height:26px;font-size:16px; font-weight:bold;
                text-align:center;
        }
        span.errorMsg {
                color:red;
        }
    </style>
    <title>Login Page</title>
</head>

<body>
    <h1>Login Page</h1><br>

    <form action="<%=request.getContextPath()%>/SecurityServlet" method="post" >	
        <p>
            User Name: <input type="text" name="username" tabindex=1><br><br>
            Password : <input type="password" name="password" tabindex=2><br><br>
            <!-- type="submit" in button is not necessary in this case -->
            <button id="submitButton" type="submit" onclick="toSubmit()" tabindex=3>Login</button>
            <br><br>
            <span class="errorMsg">${error}</span>
        </p>
    </form>

</body>
</html>