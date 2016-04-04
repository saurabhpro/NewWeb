<%--
  Created by IntelliJ IDEA.
  User: AroraA
  Date: 16-03-2016
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp" ng-controller="uploadBiometricController" lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="../css/reset.css"> <!-- CSS reset -->
    <link rel="stylesheet" href="../css/style.css"> <!-- Resource style -->
    <script src="../js/modernizr.js"></script> <!-- Modernizr -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <script src="../js/app.js"></script>
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.0/angular.min.js"></script>

    <title>Upload Salesforce</title>
</head>

<body>
<main class="cd-main-content">
    <div class="content-wrapper">
        <form action="upEmailList" method="post" enctype="multipart/form-data">
            <input type="file" name="emailListFile"/><br/>
            <input type="submit" value="Upload Email Excel"/>
        </form>
    </div> <!-- .content-wrapper -->
</main> <!-- .cd-main-content -->
<script src="../js/jquery-2.1.4.js"></script>
<script src="../js/jquery.menu-aim.js"></script>
<script src="../js/main.js"></script>
</body>
</html>
