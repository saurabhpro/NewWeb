<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp" ng-controller="GeneratePublicHolidayController" lang="en" class="no-js">
<div ng-include="GeneratePublicHoliday.jsp"></div>
<head>
    <!-- Meta Tags -->
    <meta charset="UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">

    <!-- js scripts, since there could be dependencies problem, maintain the inclusion order -->
    <script src="bower_components/angular/angular.js"></script>
    <script src="bower_components/angular-route/angular-route.js"></script>
    <script src="bower_components/angular-animate/angular-animate.js"></script>
    <script src="bower_components/angular-bootstrap/ui-bootstrap-tpls.js"></script>

    <script src="js/app.js"></script>
    <script language="javascript">
        function LoadOnce() {
            window.location.reload();
        }
    </script>

    <!-- jsp scriptlet -->
    <%
        Cookie[] cookies = request.getCookies();
        String headline = "List of Employees Who Worked on Public Holidays";
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals("month"))
                    headline = "Employees Who Worked on Public Holidays in " + cookie.getValue();
    %>
</head>

<body onload="LoadOnce()">
<main class="cd-main-content">
    <div class="content-wrapper">
        <br>
        <h2 style="text-align: center">
            <%=headline%>
        </h2>

        <br><br>
        <form>
            <br/>
            <br/>
            <br/>

            <table name="empListTable" class="table table-responsive table-striped">
                <thead class="thead-inverse">
                <tr style="font-weight:bolder;">

                    <th style="width: 2em">DATE [HOLIDAY]</th>
                    <th style="width: 1em">EMPLOYEE ID</th>
                    <th style="width: 1em">SALESFORCE ID</th>
                    <th style="width: 1em">EMPLOYEE NAMES</th>
                    <th style="width: 2em">EMPLOYEE EMAILS</th>
                    <th style="width: 1em">CHECK-IN</th>
                    <th style="width: 1em">CHECK-OUT</th>
                    <th style="width: 1em">WORK HOURS</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="item in rowCollection">

                    <td>{{item.perDayAttendanceRecord.currentDate}} <strong>[{{item.holiday}}]</strong></td>
                    <td ng-model="item.empRevalId">{{item.basicEmployeeDetails.empId}}</td>
                    <td>{{item.basicEmployeeDetails.salesForceId}}</td>
                    <td>{{item.basicEmployeeDetails.emailId}}</td>
                    <td>{{item.basicEmployeeDetails.name}}</td>
                    <td>{{item.perDayAttendanceRecord.checkIn}}</td>
                    <td>{{item.perDayAttendanceRecord.checkOut}}</td>
                    <td>{{item.perDayAttendanceRecord.workTimeForDay}}</td>
                </tr>
                </tbody>
            </table>
        </form>
    </div> <!-- .content-wrapper -->
</main> <!-- .cd-main-content -->
<script src="js/main.js"></script> <!-- Resource jQuery -->
</body>
</html>
