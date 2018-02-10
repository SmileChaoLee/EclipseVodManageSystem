<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <form action="<%=request.getContextPath()%>/LogoutServlet" method="post">
        <button type="submit" style="font-size:16px; color:red; background-color:lightgrey; font-weight:bold;">logout</button>
    </form>
</body>
</html>