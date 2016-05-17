<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html ng-app="sampleApp" ng-controller="GenerateReportController" lang="en" class="no-js">
<div ng-include="GenerateReport.jsp"></div>
<head>
    <meta charset="UTF-8">

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
    <!-- since there could be dependencies problem, maintain the inclusion order -->
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
    <%
        Cookie[] cookies = request.getCookies();
        String headline = "Monthly Report of All Employees";
        if (cookies != null)
            for (Cookie cookie : cookies)
                if (cookie.getName().equals("month"))
                    headline = "Attendance Report of All Employees for " + cookie.getValue();
    %>
</head>
<body onload="LoadOnce()">

<main class="cd-main-content">
    <div class="content-wrapper">
        <br>
        <h2 style="text-align: center">
            <%=headline%>
        </h2>
        <form action="multigen" method="post">
            <input type="hidden" name="fileToUse" value="AllWorkers">
            <button class="btn btn-danger" style="float: right;" type="submit" ng-click="showSelected()"
                    name="DownloadButtonType"
                    value="DOWNLOAD_PDF">
                DOWNLOAD PDF
            </button>
            <button class="btn btn-success" style="float: right;" type="submit" ng-click="showSelected()"
                    name="DownloadButtonType"
                    value="DOWNLOAD_EXCEL">
                DOWNLOAD EXCEL
            </button>
            <input type="hidden" name="listOfIds" value={{itemsSelected}}>
        </form>
        <form>

            <br/>
            <br/>
            <br/>

            <table st-table="rowCollection" name="empListTable" class="table table-responsive table-striped">
                <thead class="thead-inverse">
                <tr style="font-weight:bolder;">
                    <th style="width: 40px">
                        <input type="checkbox" ng-model="selectedAll" ng-click="checkAll()"/>
                    </th>
                    <th style="width: 1em">EMPLOYEE ID</th>
                    <th style="width: 1em">SALESFORCE ID</th>
                    <th style="width: 1em">EMPLOYEE NAMES</th>
                    <th style="width: 2em">EMPLOYEE EMAILS</th>
                    <th style="width: 1em">AVERAGE CHECK-IN</th>
                    <th style="width: 1em">AVERAGE CHECK-OUT</th>
                    <th style="width: 1em">AVERAGE WORK HOURS</th>
                    <th style="width: 1em">CLARIFICATION NEEDED</th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="item in rowCollection">

                    <td><input type="checkbox" ng-bind="modelName" ng-model="item.Selected" ng-click="checkStatus()">
                    </td>
                    <td ng-model="item.empRevalId">{{item.empRevalId}}</td>
                    <td>{{item.empSalesforceId}}</td>
                    <td>{{item.empName}}</td>
                    <td>{{item.empEmailId}}</td>
                    <td>{{item.empAvgCheckInTimeForMonth}}</td>
                    <td>{{item.empAvgCheckOutTimeForMonth}}</td>
                    <td>{{item.empAvgWorkHoursForMonth}}</td>
                    <td>{{item.empIfClarificationNeeded}}</td>
                    <td>
                        <button type="button" class="btn btn-info-outline" value="View Details"
                                ng-click="open(item,'lg')">View Details
                        </button>
                    </td>

                </tr>
                </tbody>
            </table>

        </form>

    </div> <!-- .content-wrapper -->
</main> <!-- .cd-main-content -->
<script src="js/main.js"></script> <!-- Resource jQuery -->

</body>
</html>
