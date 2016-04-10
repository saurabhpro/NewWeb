<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp" ng-controller="ReportController" lang="en">
<%@ page import="servlets.login.SignInServlet" %>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,700' rel='stylesheet' type='text/css'>

  <link rel="stylesheet" href="css/reset.css"> <!-- CSS reset -->
  <link rel="stylesheet" href="css/style.css"> <!-- Resource style -->

  <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap-theme.min.css">

  <!-- since there could be dependencies problem, maintain the inculsion order -->
  <script src="bower_components/angular/angular.js"></script>
  <script src="bower_components/angular-route/angular-route.js"></script>
  <script src="bower_components/angular-animate/angular-animate.js"></script>
  <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>

  <script src="js/app.js"></script>
  <script src="bower_components/modernizr/modernizr.js"></script> <!-- Modernizr -->

  <title>Home Page</title>
</head>
<body>
<header class="cd-main-header">
  <nav class="cd-nav">
    <ul class="cd-top-nav">
      <li class="has-children account">
        <a href="#0">
          <img src="img/cd-avatar.png" alt="avatar">
          <%out.print(session.getAttribute("userName"));%>
        </a>
        <ul>
          <li><a href="#0">My Account</a></li>
          <li><a href="#0">Edit Account</a></li>
          <li><a href="logout">Logout</a></li>
        </ul>
      </li>
    </ul>
  </nav>
</header> <!-- .cd-main-header -->

<main class="cd-main-content">
  <nav class="cd-side-nav">
    <ul>
      <li class="cd-label">Main</li>
      <li class="has-children comments">
        <a href="#0">Overview</a>
      </li>
      <li class="has-children overview">
        <a href="#UploadFiles" id="upload">Upload Files</a>
      </li>

       <!--<li class="has-children overview">
       <a href="">Reports</a>
        <ul>
          <li><a href="#GenerateReport">Daily Report</a></li>
          <li><a href="#GenerateDiscrepancy">Discrepancy Report</a></li>
        </ul>

      </li>
      -->
      <li class="has-children notifications active upload">

        <a href="" id="reports" ng-click="clickFunc()">Reports</a>

        <ul ng-show="showMe">
          <li><a href="#GenerateReport">Monthly Report</a></li>
          <li><a href="#GenerateDiscrepancy">Discrepancy Report</a></li>
          <li><a href="#GenerateWeekendHoliday">Weekend Holiday</a></li>
          <li><a href="#GeneratePublicHoliday">Public Holiday</a></li>

          <!--<li><a href="#uploadBiometric">Biometric File</a></li>
          <li><a href="#uploadSalesforce">Financial Force File </a></li>
          <li><a href="#uploadEmailList">Email List File</a></li>
          <li><a href="#uploadHoliday">Holiday List File</a></li>
          -->
        </ul>

      </li>


      <li class="has-children comments">
        <a href="#0">Sent Email</a>


      </li>
    </ul>

    <ul>
      <li class="cd-label">Secondary</li>

      <li class="has-children users">
        <a href="#0">Users</a>

        <ul>
          <li><a href="#0">All Users</a></li>
          <li><a href="#0">Edit User</a></li>
          <li><a href="#0">Add User</a></li>
        </ul>
      </li>
    </ul>

  </nav>
  <div ng-view></div>
</main> <!-- .cd-main-content -->

<script src="bower_components/jquery/dist/jquery.js"></script>
<script src="bower_components/jQuery-menu-aim/jquery.menu-aim.js"></script>
<script src="js/main.js"></script> <!-- Resource jQuery -->

</body>
</html>