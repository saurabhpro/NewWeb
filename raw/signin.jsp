<%--
  Created by IntelliJ IDEA.
  User: AroraA
  Date: 05-04-2016
  Time: 09:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:useBean id="sessionID" class="login.SignInServlet" scope="session"/>

<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <link rel="stylesheet" href="../web/css/reset.css"> <!-- CSS reset -->
    <link rel="stylesheet" href="../web/css/style.css"> <!-- Resource style -->
    <link rel="stylesheet" href="../web/css/customStyle.css">
</head>
<body>
<header>
    <nav class="top-bar" data-topbar>
        <ul class="title-area">
            <!-- Remove the class "menu-icon" to get rid of menu icon. Take out "Menu" to just have icon alone -->
            <li class="toggle-topbar menu-icon"><a href="#"><span>Menu</span></a></li>
        </ul>
        <div class="logo">
            <a href="http://www.reval.com/">
                <img src="../web/img/RevalLogo.jpg"/>
            </a>
        </div>
        <!--      <section class="top-bar-section">
                  <!-- Right Nav Section -->
        <!--        <ul class="center-buttons">
                    <li class="divider"></li>
                    <li><a href="#">Centered Button</a></li>
                    <li class="divider"></li>
                    <li><a href="#">Centered Button</a></li>
                    <li class="divider"></li>
                </ul>
            </section> -->
    </nav>
</header>
<main class="cd-main-content">
    <div class="row">
        <div class="medium-6 medium-centered large-4 large-centered columns">

            <form action="signin" method="post">
                <div class="row column log-in-form">
                    <h4 class="text-center">Log in with you email account</h4>
                    <label>Email
                        <input type="email" placeholder="somebody@example.com" name="userName" required>
                        <jsp:setProperty name="sessionID" property="userName" value="userName"></jsp:setProperty>
                    </label>
                    <label>Password
                        <input type="password" placeholder="Password" name="password" required>
                        <jsp:setProperty name="sessionID" property="password" value=""

                    </label>
                    <input id="show-password" type="checkbox"><label for="show-password">Show password</label>
                    <p><input type="submit" class="button" style="width: 100%" value="Log In"></p>
                    <p class="text-center"><a href="#">userName = reval@gmail</a></p>

                    <p class="text-center"><a href="#">password = Test</a></p>
                    <jsp:setProperty name="sessionID" property="*"></jsp:setProperty>
                </div>
            </form>
        </div>
    </div>
</main>

<link rel="stylesheet" href="../web/css/customStyle.css">
<link rel="stylesheet" href="../web/bower_components/foundation/css/foundation.min.css" property="">
<script type="application/javascript" src="../web/bower_components/foundation/js/foundation.min.js"></script>
</body>

