<%--
  Created by IntelliJ IDEA.
  User: AroraA
  Date: 11-04-2016
  Time: 09:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Logged Out</title>
    <script language="JavaScript" type="text/javascript">
        window.history.forward(1);
        function preventBack() {
            window.history.forward();
        }
        setTimeout("preventBack()", 0);
        window.onunload = function () {
            null
        };
    </script>
</head>
<body>
<h1>Thank You! You have successfully logged out of the system!</h1>
<a href="../web/signin.html">Click Here To Login</a>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

    if (session.getAttribute("userName") == null)
        response.sendRedirect("./signin.html");

%>
</body>
</html>